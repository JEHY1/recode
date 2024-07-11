package com.example.recode.service;

import com.example.recode.domain.History;
import com.example.recode.domain.Product;
import com.example.recode.domain.ProductImg;
import com.example.recode.dto.ProductDetailViewResponse;
import com.example.recode.dto.UploadProductRequest;
import com.example.recode.repository.HistoryRepository;
import com.example.recode.repository.ProductImgRepository;
import com.example.recode.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    //application.properties 의 app.upload.dir 값을 사용
    @Value("${app.upload.dir}")
    private String uploadDir;
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final UserService userService;
    private final HistoryService historyService;

    public Product findProductByProductId(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    public List<ProductImg> findProductImgByProductId(long productId){
        return productImgRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("not found imgs"));
    }

    //상품 아이디에 해당하는 상품중 품절된 상품 리스트를 반환
    public List<Product> findProductByProductIdInAndProductSold(List<Long> productIds){

        return productRepository.findAllByProductIdInAndProductSold(productIds, 1)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    // productId -> ProductDetailViewResponse
    @Transactional
    public ProductDetailViewResponse getProductInfoByProductId(long productId, Principal principal){
        List<String> productDetailImgs = findProductImgByProductId(productId).stream().map(productImg -> productImg.getProductImgSrc()).toList();
        Product product = findProductByProductId(productId);
        
        // 상품 조회수 증가
        product.IncProductViewCount();
        
        //히스토리 등록
        if(principal != null){
            long userId = userService.getUserId(principal);
            List<History> historyList = historyService.findAllByUserId(userId);
            History history = historyService.findByProductIdAndUserId(productId, userId);

            if(history != null){

                historyService.delete(history);
            }

            if(historyList.size() < 10){
                historyService.save(History.builder()
                        .productId(productId)
                        .userId(userId)
                        .build()
                );
            }
            else{
                historyService.delete(historyList.get(0));
                historyService.save(History.builder()
                        .productId(productId)
                        .userId(userId)
                        .build()
                );
            }
        }


        return ProductDetailViewResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productModel(product.getProductModel())
                .productRepImg(product.getProductRepresentativeImgSrc())
                .type(product.getProductType())
                .color(product.getProductColor())
                .size(product.getProductSize())
                .material(product.getProductMaterial())
                .regularPrice(product.getProductRegularPrice())
                .discountPrice(product.getProductDiscountPrice() == null ? null : product.getProductDiscountPrice())
                .productDetailImgs(productDetailImgs)
                .productSold(product.getProductSold())
                .build();
    }

    //상품 등록
    @Transactional
    public Product uploadProduct(UploadProductRequest request){

        Product product = productRepository.save(Product.builder()
                .productName(request.getProductName())
                .productModel(request.getProductModel())
                .productCategory(request.getProductCategory())
                .productRegularPrice(request.getProductRegularPrice())
                .productDiscountPrice(request.getProductDiscountPrice())
                .productSize(request.getProductSize())
                .productMaterial(request.getProductMaterial())
                .productRepresentativeImgSrc("")
                .productSold(0)
                .productColor(request.getProductColor())
                .productType(request.getProductType())
                .productViewCount(0)
                .build());

        //상품 등록시 대표이미지로 설정한 이미지파일 이름
        String originalFileName = request.getProductRepImg().getOriginalFilename();
        //파일 확장자 추출
        int extensionIndex = originalFileName.lastIndexOf(".");
        String extension = originalFileName.substring(extensionIndex);

        //db에 상품 /image/productRep/product{상품아이디}RepImg.확장자 로 db저장
        product.updateRepImgSrc("/images/productRep/product" + product.getProductId() + "RepImg" + extension);

        //파일 업로드 (application.properties 에 저장한 경로/images/productRep/)
        fileUpload(request.getProductRepImg(), product.getProductId(), extension, "productRep", "RepImg", null);

        //상세 이미지 파일들 업로드 및 db 저장
        int imgNum = 1;
        for(MultipartFile img : request.getProductExtImg()){
            originalFileName = img.getOriginalFilename();
            extensionIndex = originalFileName.lastIndexOf(".");
            extension =originalFileName.substring(extensionIndex);

            //파일 업로드 (application.properties 에 저장한 경로/images/productDetail/)
            fileUpload(img, product.getProductId(), extension, "productDetail", "detailImg", imgNum);
            productImgRepository.save(ProductImg.builder()
                    .productId(product.getProductId())
                    .productImgSrc("/images/productDetail/product" + product.getProductId() + "DetailImg" + imgNum++ + extension)
                    .build());
        }

        return product;
    }

    //파일 업로드(파일, 상품 아이디, 파일 확장자, 추가 경로, 파일 이름, 파일 번호)
    public void fileUpload(MultipartFile multipartFile, long productId, String extension, String dir, String fileNick, Integer num) {

        //경로 만들기
        Path copyOfLocation = Paths.get(uploadDir + File.separator + dir + File.separator + "product" + productId + fileNick + (num == null ? "" : num) + extension);
        try {
            // inputStream 사용
            // copyOfLocation 저장위치
            // 기존 파일이 존재할 경우 덮어쓰기
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IllegalArgumentException("Could not store file : " + multipartFile.getOriginalFilename());
        }

    }

    public List<Product> findProductAllByProductIdIn(List<Long> productIds){
        return productRepository.findAllByProductIdIn(productIds)
                .orElse(null);
    }

    public List<Product> newProduct() {
        return productRepository.newProduct()
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    public Page<Product> findProductByProductCategoryAndProductSold(String productCategory, int productSold, Pageable pageable){

        return productRepository.findAllByProductCategoryAndProductSold(productCategory, productSold, pageable)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));
    }

    public Page<Product> findProductByProductNameContainingAndProductSold(String searchText, int productSold, Pageable pageable){

        return productRepository.findAllByProductNameContainingAndProductSold(searchText, productSold, pageable)
                .orElseThrow(() -> new IllegalArgumentException("not found product"));

    }

    public Page<Product> searchProduct(String searchText, String productCategory, Pageable pageable){

        if(searchText != null){
            return findProductByProductNameContainingAndProductSold(searchText, 0, pageable);
        }
        else if(productCategory != null){
            return findProductByProductCategoryAndProductSold(productCategory, 0, pageable);
        }

        return null;
    }

    //최근 본 상품목록 리스트
    public List<Product> getRecentViewProductList(Principal principal){
        List<History> historyList = historyService.findAllByUserId(userService.getUserId(principal));

        return findProductAllByProductIdIn(historyList.stream().mapToLong(History::getProductId).boxed().collect(Collectors.toList()));
    }

    public List<Product> findByProductNameContaining(String productName) { // productName 을 포함하는 List<Product>
        return productRepository.findByProductNameContaining(productName).orElse(null);
    }

    public void deleteById(Long productId) { // productId로 Product 삭제
        productRepository.deleteById(productId);
    }
}
