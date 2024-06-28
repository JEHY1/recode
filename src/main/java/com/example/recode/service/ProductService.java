package com.example.recode.service;

import com.example.recode.domain.Product;
import com.example.recode.domain.ProductImg;
import com.example.recode.dto.ProductDetailViewResponse;
import com.example.recode.dto.UploadProductRequest;
import com.example.recode.repository.ProductImgRepository;
import com.example.recode.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    //application.properties 의 app.upload.dir 값을 사용
    @Value("${app.upload.dir}")
    private String uploadDir;
    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;

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
    public ProductDetailViewResponse getProductInfoByProductId(long productId){
        List<String> productDetailImgs = findProductImgByProductId(productId).stream().map(productImg -> productImg.getProductImgSrc()).toList();
        Product product = findProductByProductId(productId);

        return ProductDetailViewResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productModel(product.getProductModel())
                .productRepImg(product.getProductRepresentativeImgSrc())
                .type(product.getProductType())
                .color(product.getProductColor())
                .regularPrice(product.getProductRegularPrice())
                .discountPrice(product.getProductDiscountPrice() == null ? null : product.getProductDiscountPrice())
                .productDetailImgs(productDetailImgs)
                .productSold(product.getProductSold())
                .build();
    }

    //상품 등록
    @Transactional
    public Product uploadProduct(UploadProductRequest request){

        System.err.println("1");
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
                .build());

        System.err.println("1");
        //상품 등록시 대표이미지로 설정한 이미지파일 이름
        String originalFileName = request.getProductRepImg().getOriginalFilename();
        //파일 확장자 추출
        int extensionIndex = originalFileName.lastIndexOf(".");
        String extension = originalFileName.substring(extensionIndex);

        System.err.println("1");
        //db에 상품 /image/productRep/product{상품아이디}RepImg.확장자 로 db저장
        product.updateRepImgSrc("/images/productRep/product" + product.getProductId() + "RepImg" + extension);

        System.err.println("1");
        //파일 업로드 (application.properties 에 저장한 경로/images/productRep/)
        fileUpload(request.getProductRepImg(), product.getProductId(), extension, "productRep", "RepImg", null);

        System.err.println("1");
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

        System.err.println("1");
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
            e.printStackTrace();
            throw new IllegalArgumentException("Could not store file : " + multipartFile.getOriginalFilename());
        }

    }
}
