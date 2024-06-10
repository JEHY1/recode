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

    document.getElementById('salePercentage').textContent = parseInt(100 - parseInt(document.getElementById('productDiscountPrice').value) * 100 / parseInt(document.getElementById('productRegularPrice').value));
}


