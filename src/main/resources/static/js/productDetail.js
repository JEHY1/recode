function httpRequest(url, method, body){
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type" : "application/json"
        },
        body: body
    });
}

const productDetailInfoButton = document.getElementById('product-detail-info-btn');

if(productDetailInfoButton){
    productDetailInfoButton.addEventListener('click', () => {
        productDetailInfoButton.classList.remove('border-left-gainsboro', 'border-top-gainsboro', 'color-gainsboro');
        productDetailInfoButton.classList.add('border-left-black', 'border-top-black', 'border-bottom-white', 'color-black');

        productSaleInfoButton.classList.remove('border-left-ginsboro', 'border-top-black', 'border-left-gainsboro', 'border-bottom-white', 'color-black');
        productSaleInfoButton.classList.add('border-left-black', 'border-top-gainsboro', 'border-right-gainsboro', 'color-gainsboro');

        productQnAButton.classList.remove('border-top-black', 'border-right-black', 'border-bottom-white');
        productQnAButton.classList.add('border-top-gainsboro', 'border-right-gainsboro');

        document.getElementById('product-detail-info-tap').classList.remove('d-hidden');
        document.getElementById('product-sale-info-tap').classList.add('d-hidden');
        document.getElementById('product-QnA-tap').classList.add('d-hidden');

    });
}

const productSaleInfoButton = document.getElementById('product-sale-info-btn');

if(productSaleInfoButton){
    productSaleInfoButton.addEventListener('click', () => {
        productDetailInfoButton.classList.remove('border-left-black', 'border-top-black', 'border-bottom-white', 'color-black');
        productDetailInfoButton.classList.add('border-left-gainsboro', 'border-top-gainsboro', 'color-gainsboro');

        productSaleInfoButton.classList.remove('border-top-gainsboro', 'border-left-gainsboro', 'border-right-gainsboro', 'color-gainsboro');
        productSaleInfoButton.classList.add('border-top-black', 'border-right-black', 'border-left-black', 'border-bottom-white', 'color-black');

        productQnAButton.classList.remove('border-top-black', 'border-right-black', 'border-bottom-white');
        productQnAButton.classList.add('border-top-gainsboro', 'gorder-right-gainsboro', 'color-gainsboro');

        document.getElementById('product-sale-info-tap').classList.remove('d-hidden');
        document.getElementById('product-detail-info-tap').classList.add('d-hidden');
        document.getElementById('product-QnA-tap').classList.add('d-hidden');

    });
}

const productQnAButton = document.getElementById('product-QnA-btn');

if(productQnAButton){
    productQnAButton.addEventListener('click', () => {
        productDetailInfoButton.classList.remove('border-left-black', 'border-top-black', 'border-bottom-white', 'color-black');
        productDetailInfoButton.classList.add('border-left-gainsboro', 'border-top-gainsboro', 'color-gainsboro');

        productSaleInfoButton.classList.remove('border-left-black', 'border-top-black', 'border-bottom-white', 'border-right-gainsboro', 'color-black');
        productSaleInfoButton.classList.add('border-left-gainsboro', 'border-top-gainsboro', 'border-right-black', 'color-gainsboro');

        productQnAButton.classList.remove('border-top-gainsboro', 'border-right-gainsboro', 'color-gainsboro');
        productQnAButton.classList.add('border-top-black', 'border-right-black', 'border-bottom-white', 'color-black');

        document.getElementById('product-sale-info-tap').classList.add('d-hidden');
        document.getElementById('product-detail-info-tap').classList.add('d-hidden');
        document.getElementById('product-QnA-tap').classList.remove('d-hidden');
    });
}

const QnATitleButtons = document.getElementsByClassName('QnATitle-btn');

if(QnATitleButtons){
    Array.from(QnATitleButtons).forEach(button => {
        button.addEventListener('click', () => {
            console.log('click');
            if(button.nextElementSibling.classList.contains('d-hidden')){
                button.nextElementSibling.classList.remove('d-hidden');
            }
            else{
                button.nextElementSibling.classList.add('d-hidden');
            }
        });
    });
}

if(document.getElementById('productDiscountPrice').value !== ''){
    document.getElementById('salePercentage').textContent = parseInt(100 - parseInt(document.getElementById('productDiscountPrice').value) * 100 / parseInt(document.getElementById('productRegularPrice').value)) + '%';
}

const moveTopButton = document.getElementById('move-top-btn');

if(moveTopButton){
    moveTopButton.addEventListener('click', () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });
}

const moveBottomButton = document.getElementById('move-bottom-btn');

if(moveBottomButton){
    moveBottomButton.addEventListener('click', () => {
        console.log('click');
        window.scrollTo({
            top: document.body.scrollHeight,
            behavior: 'smooth'
        });
    });
}

const pages = document.getElementsByClassName('pages');

if(pages){
    pages[0].classList.remove('d-hidden');
    pages[0].setAttribute('id', 'QnA-show');
}

const QnAPageUpButton = document.getElementById('QnAPageUp-btn');

if(QnAPageUpButton){
    QnAPageUpButton.addEventListener('click', () => {
        if(document.getElementById('QnA-show').nextElementSibling != null){
            let currentPage = document.getElementById('QnA-show');
            currentPage.setAttribute('id', '');
            currentPage.classList.add('d-hidden');
            currentPage.nextElementSibling.setAttribute('id', 'QnA-show');
            document.getElementById('QnA-show').classList.remove('d-hidden');

            let pageCount = document.getElementById('QnAPage');
            pageCount.textContent = parseInt(pageCount.textContent) + 1;
            parseInt(document.getElementById('QnAPage').textContent)

        }
        else{
            alert('마지막 페이지입니다.');
        }
    });
}

const QnAPageDownButton = document.getElementById('QnAPageDown-btn');

if(QnAPageDownButton){
    QnAPageDownButton.addEventListener('click', () => {
        if(document.getElementById('QnAPage').textContent !== '1'){
            let currentPage = document.getElementById('QnA-show');
            currentPage.setAttribute('id', '');
            currentPage.classList.add('d-hidden');

            currentPage.previousElementSibling.setAttribute('id', 'QnA-show');
            document.getElementById('QnA-show').classList.remove('d-hidden');

            let pageCount = document.getElementById('QnAPage');
            pageCount.textContent = parseInt(pageCount.textContent) - 1;
            parseInt(document.getElementById('QnAPage').textContent)
        }
        else{
            alert('이전 페이지가 없습니다.')
        }
    });
}

const cartButton = document.getElementById('cart-btn');

if(cartButton){
    cartButton.addEventListener('click', () => {
        let body = JSON.stringify({
            productId : document.getElementById('productId').value
        });


        httpRequest(`/user/addCart`, 'POST', body)
        .then(response => {
            if(response.ok){
                alert('장바구니에 등록되었습니다.')
            }
            else{
                alert('error');
            }
        })
    })
}






