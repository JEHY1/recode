const addressInfoOpenButton = document.getElementById('addressInfoOpen-btn');

if(addressInfoOpenButton){
    addressInfoOpenButton.addEventListener('click', () => {
        if(addressInfoOpenButton.nextElementSibling.classList.contains('d-hidden')){
            addressInfoOpenButton.nextElementSibling.classList.remove('d-hidden');
        }
        else{
            addressInfoOpenButton.nextElementSibling.classList.add('d-hidden');
        }
    })
}

const deliveryRequestButton = document.getElementsByClassName('deliveryRequest-btn');

if(deliveryRequestButton){
    Array.from(deliveryRequestButton).forEach(button => {
        button.addEventListener('click', () => {
            console.log('click');
            button.children[0].setAttribute('src', '/images/icon_img/addressCheck.png');
            document.getElementById('deliveryRequest').value = button.children[1].textContent;

            Array.from(deliveryRequestButton).forEach(other => {
                if(button !== other){
                    other.children[0].setAttribute('src', '/images/icon_img/addressUncheck.png')
                }
            });

            console.log(button.nextElementSibling.id);
            if(button.nextElementSibling.id === 'deliveryBoxNumInputPlace'){
                document.getElementById('deliveryBoxNumInputPlace').classList.remove('d-hidden');
                document.getElementById('deliveryBoxNum').value = deliveryBoxNumView.value;
            }
            else{
                document.getElementById('deliveryBoxNumInputPlace').classList.add('d-hidden');
                document.getElementById('deliveryBoxNum').value = '';
            }
        });
    });
}

const deliveryBoxNumView = document.getElementById('deliveryBoxNumView');

if(deliveryBoxNumView){
    deliveryBoxNumView.addEventListener('blur', () => {
        document.getElementById('deliveryBoxNum').value = deliveryBoxNumView.value;
    });
}

const notUsedPasswordButton = document.getElementById('notUsedPassword-btn');

if(notUsedPasswordButton){
    notUsedPasswordButton.addEventListener('click', () => {
        notUsedPasswordButton.children[0].setAttribute('src', '/images/icon_img/addressCheck.png');
        document.getElementById('frontDoorSecret').value = '';
        usedPasswordButton.children[0].setAttribute('src', '/images/icon_img/addressUncheck.png');
    });
}

const usedPasswordButton = document.getElementById('usedPassword-btn');

if(usedPasswordButton){
    usedPasswordButton.addEventListener('click', () => {
        usedPasswordButton.children[0].setAttribute('src', '/images/icon_img/addressCheck.png');
        document.getElementById('frontDoorSecret').value = usedPasswordButton.children[1].value;
        notUsedPasswordButton.children[0].setAttribute('src', '/images/icon_img/addressUncheck.png');
    });

    usedPasswordButton.children[1].addEventListener('blur', () => {
        document.getElementById('frontDoorSecret').value = usedPasswordButton.children[1].value;
    });
}

const getNewAddressFormButton = document.getElementById('getNewAddressForm-btn');

if(getNewAddressFormButton){
    getNewAddressFormButton.addEventListener('click', () => {
        console.log('click');
        document.getElementById('addressNickname').classList.add('d-hidden');
    });
}

const selectBar = document.getElementById('selectBar');

if(selectBar){
    selectBar.addEventListener('change', () => {
        console.log('change');
        if(selectBar.value === '-1'){
            console.log("new form");
            document.getElementById('addressNickname').parentElement.classList.add('d-hidden');
            document.getElementById('deliveryRequestSel').classList.remove('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.classList.add('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.nextElementSibling.classList.remove('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.nextElementSibling.nextElementSibling.classList.add('d-hidden');
        }
        else{
            document.getElementById('addressNickname').parentElement.classList.remove('d-hidden');
            document.getElementById('deliveryRequestSel').classList.add('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.classList.remove('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.nextElementSibling.classList.add('d-hidden');
            document.getElementById('deliveryRequestSel').nextElementSibling.nextElementSibling.nextElementSibling.classList.remove('d-hidden');
        }
    });
}