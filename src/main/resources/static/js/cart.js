function httpRequest(url, method, body){
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type" : "application/json"
        },
        body: body
    });
}

function toList(components){
    let list = [];
    components.forEach(component => list.push(component.value));
    return list;
}

function selectToList(selects, values){
    let list = [];
    selects.forEach((select, index) => {
        if(select.checked){
            list.push(values[index].value);
        }
    });

    return list;
}

const allCheckBox = document.getElementById('allCheckBox');

if(allCheckBox){
    allCheckBox.addEventListener('click', () => {
        console.log('changed');
        if(allCheckBox.checked){
            if(productCheckBox){
                Array.from(productCheckBox).forEach(checkBox => {
                    if(!checkBox.checked){
                        checkBox.click();
                    }
                });
            }
        }
        else{
            if(productCheckBox){
                Array.from(productCheckBox).forEach(checkBox => {
                    if(checkBox.checked){
                        checkBox.click();
                    }
                });
            }
        }
    });
}

const productCheckBox = document.getElementsByClassName('productCheckBox');

if(productCheckBox){
    Array.from(productCheckBox).forEach(checkBox => {
        checkBox.addEventListener('click', () => {
            let discountPrice = checkBox.nextElementSibling.nextElementSibling.nextElementSibling.children[0].children[1].textContent;
            discountPrice = parseInt(discountPrice.substring(0, discountPrice.length - 1));
            let regularPrice = checkBox.nextElementSibling.nextElementSibling.textContent;
            regularPrice = parseInt(regularPrice.substring(0, regularPrice.length - 1));
            let discount = regularPrice - discountPrice;

            let totalRegularPrice = document.getElementById('totalRegularPrice').textContent;
            totalRegularPrice = parseInt(totalRegularPrice.substring(0, totalRegularPrice.length - 1));
            let totalDiscount = document.getElementById('totalDiscount').textContent;
            totalDiscount = parseInt(totalDiscount.substring(0, totalDiscount.length - 1));
            let totalDiscountPrice = document.getElementById('totalDiscountPrice').textContent;
            totalDiscountPrice = parseInt(totalDiscountPrice.substring(0, totalDiscountPrice.length - 1));

            if(checkBox.checked){
                document.getElementById('totalRegularPrice').textContent = totalRegularPrice + regularPrice + '원';
                document.getElementById('totalDiscount').textContent = totalDiscount + discount + '원';
                document.getElementById('totalDiscountPrice').textContent = totalDiscountPrice + discountPrice + '원';

                let productForm = document.createElement('input');
                productForm.name = 'cartId';
                productForm.id = 'cartIdForm' + checkBox.previousElementSibling.value;
                productForm.type = 'hidden';
                productForm.value = checkBox.previousElementSibling.value;

                document.getElementById('selectedProductField').appendChild(productForm);

                let allCheckFlag = 1;
                Array.from(productCheckBox).forEach(checkBox2 => {
                    if(!checkBox2.checked){
                        allCheckFlag = 0;
                    }
                });
                console.log(allCheckFlag);
                if(allCheckFlag === 1){
                    allCheckBox.checked = true;
                }
            }
            else{
                console.log('cartIdForm' + checkBox.previousElementSibling.value);
                console.log(document.getElementById('cartIdForm' + checkBox.previousElementSibling.value));
                let comp = document.getElementById('cartIdForm' + checkBox.previousElementSibling.value);
                comp.remove();

                document.getElementById('totalRegularPrice').textContent = totalRegularPrice - regularPrice + '원';
                document.getElementById('totalDiscount').textContent = totalDiscount - discount + '원';
                document.getElementById('totalDiscountPrice').textContent = totalDiscountPrice - discountPrice + '원';

                allCheckBox.checked = false;
            }
        });
    });
}

const cartDeleteButton = document.getElementsByClassName('cartDelete-btn');

if(cartDeleteButton){
    Array.from(cartDeleteButton).forEach(button => {
        button.addEventListener('click', () => {
            console.log('click');
            let body = JSON.stringify({
                cartId: button.previousElementSibling.previousElementSibling.value
            });

            httpRequest(`/user/cart`, 'DELETE', body)
            .then(response => {
                if(response.ok){
                    alert('장바구니에서 삭제되었습니다.');
                    location.replace('/user/cart');
                }
                else{
                    alert('error');
                }
            });
        });
    });
}

const deleteSoldProductButton = document.getElementById('deleteSoldProduct-btn');

if(deleteSoldProductButton){
    deleteSoldProductButton.addEventListener('click', () => {
        let body = JSON.stringify({
            cartIds : toList(Array.from(document.getElementsByClassName('cartIds')))
        });

        httpRequest(`/user/cart/cleanList`, 'DELETE', body)
        .then(response => {
            if(response.ok){
                return response.json();
            }
            else{
                alert('error');
                throw new error('error');
            }
        })
        .then(data => {
            console.log(data);
            if(data.products.length === 0){
                alert('품절된 상품이 없습니다.');
            }
            else{
                let text = '';
                data.products.forEach(product => text += product.productName + '\n');
                alert('품절된 상품 있음\n' + text + '\n\n 해당 상품이 장바구니에서 제외되었습니다.');
                location.replace('/user/cart');
            }
        });
    });
}

const deleteSelectProductButton = document.getElementById('deleteSelectProduct-btn');

if(deleteSelectProductButton){
    deleteSelectProductButton.addEventListener('click', () => {
        let cartIds = selectToList(Array.from(document.getElementsByClassName('productCheckBox')), Array.from(document.getElementsByClassName('cartIds')));
        console.log(cartIds);

        if(cartIds.length === 0){
            alert('상품을 선택하세요');
            return;
        }

        let body = JSON.stringify({
            cartIds : cartIds
        });

        httpRequest(`/user/cartList`, 'DELETE', body)
        .then(response => {
            if(response.ok){
                alert('삭제 완');
                location.replace('/user/cart');
            }
            else{
                alert('error');
            }
        });
    });
}

const allOrderButton = document.getElementById('allOrder-btn');

if(allOrderButton){
    allOrderButton.addEventListener('click', () => {
        if(!allCheckBox.checked){
            allCheckBox.click();
        }

        document.getElementById('selectOrder-btn').click();
    });
}

const selectOrderButton = document.getElementById('selectOrder-btn');

if(selectOrderButton){
    selectOrderButton.addEventListener('click', () => {
        let cartIds = selectToList(Array.from(document.getElementsByClassName('productCheckBox')), Array.from(document.getElementsByClassName('cartIds')));
        console.log(cartIds);

        if(cartIds.length === 0){
            alert('상품을 선택하세요');
            return;
        }
        document.getElementById('selectedProductField').submit();

    })
}
