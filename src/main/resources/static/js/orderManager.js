function httpRequest(url, method, body){
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type" : "application/json"
        },
        body: body
    });
}

function toCheckValueList(comps){
    let list = [];
    comps.forEach(checkBox => {
        if(checkBox.checked){
            list.push(checkBox.nextElementSibling.value);
        }
    });

    return list;
}

function wait(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function toWon(price){
    let PriceText = '';
    price += '';

    while(price.length > 3){
        console.log(price.substring(price.length - 3, price.length));
        PriceText += ',' + price.substring(price.length - 3, price.length);
        price = price.substring(0, price.length - 3);
        console.log(price);
        console.log(PriceText);
    }
    console.log(price + PriceText + '원');
    return price + PriceText + '원';
}

function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

//페이지 이동
function movePage(comp){
    //현제 페이지의 체크박스 모두 해제
    Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox')).forEach(checkBox => checkBox.checked = false);
    document.getElementById('allSelectCheckBox').checked = false;

    //페이지 이동하기
    document.getElementById('selectedPage').classList.add('d-hidden');
    document.getElementById('selectedPage').setAttribute('id', '');
    document.getElementById('itemField').children[parseInt(comp.textContent) - 1].setAttribute('id', 'selectedPage');
    document.getElementById('selectedPage').classList.remove('d-hidden');

    document.getElementById('selectedPage-btn').setAttribute('id', '');
    comp.setAttribute('id', 'selectedPage-btn');

    if(document.getElementById('selectedPage').previousElementSibling === null){
        prevPageButton.setAttribute('disabled', '');
    }
    else{
        prevPageButton.removeAttribute('disabled');
    }

    if(document.getElementById('selectedPage').nextElementSibling === null){
        nextPageButton.setAttribute('disabled', '');
    }
    else{
        nextPageButton.removeAttribute('disabled');
    }

}

function getProductName(Comp){
    document.getElementById('productNameSearch').value = Comp.children[0].textContent + Comp.children[1].textContent + Comp.children[2].textContent;
    document.getElementById('productNameSearchedField').classList.add('d-hidden');
}

function getUserName(Comp){
    document.getElementById('userRealNameSearch').value = Comp.children[0].textContent + Comp.children[1].textContent + Comp.children[2].textContent;
    document.getElementById('UserRealNameSearchedField').classList.add('d-hidden');
}

function getPeriod(Comp){
    Array.from(document.getElementsByClassName('period-btn')).forEach(button => {
        button.classList.remove('bg-gray');
        button.classList.remove('color-white');
        button.classList.add('bg-white');
    });
    Comp.classList.remove('bg-white');
    Comp.classList.add('bg-gray');
    Comp.classList.add('color-white');

    let body = JSON.stringify({
        unitPeriod : Comp.children[0].value
    });

    httpRequest(`/admin/getServerDate`, 'POST', body)
    .then(response => {
        if(response.ok){
            return response.json();
        }
    })
    .then(data => {
        console.log(data);
        document.getElementById('startYearSel').value = data.startYear;
        document.getElementById('startMonthSel').value = data.startMonth;
        document.getElementById('startDaySel').value = data.startDay;
        document.getElementById('endYearSel').value = data.endYear;
        document.getElementById('endMonthSel').value = data.endMonth;
        document.getElementById('endDaySel').value = data.endDay;
    });
}

const productNameSearch = document.getElementById('productNameSearch');

if(productNameSearch){
    productNameSearch.addEventListener('focus', () => document.getElementById('productNameSearchedField').classList.remove('d-hidden'));
    productNameSearch.addEventListener('blur', () => {
        wait(100)
        .then(() => document.getElementById('productNameSearchedField').classList.add('d-hidden'));
    });


    productNameSearch.addEventListener('input', () => {
        if(productNameSearch.value !== ''){
            let body = JSON.stringify({
                productName : productNameSearch.value
            });

            httpRequest(`/admin/getIncludeNameList`, 'POST', body)
            .then(response => {
                if(response.ok){
                    return response.json();
                }
            })
            .then(data => {

                removeAllChildNodes(document.getElementById('productNameSearchedField'));
                console.log(data);
                data.forEach(result => {
                    let fullText = document.createElement('div');
                    fullText.setAttribute('class', 'row w-100 mx-auto ps-2 productNameField');
                    fullText.setAttribute('onclick', 'getProductName(this)');

                    let frontText = document.createElement('div');
                    frontText.setAttribute('class', 'col-auto p-0');
                    frontText.textContent = result.frontText;

                    let searchText = document.createElement('div');
                    searchText.setAttribute('class', 'col-auto p-0 color-blue');
                    searchText.textContent = result.searchText;

                    let endText = document.createElement('div');
                    endText.setAttribute('class', 'col-auto p-0');
                    endText.textContent = result.endText;

                    document.getElementById('productNameSearchedField').appendChild(fullText);
                    fullText.appendChild(frontText);
                    fullText.appendChild(searchText);
                    fullText.appendChild(endText);

                });

            })
        }
        else{
            removeAllChildNodes(document.getElementById('productNameSearchedField'));
        }
    });
}

const userRealNameSearch = document.getElementById('userRealNameSearch');

if(userRealNameSearch){
    userRealNameSearch.addEventListener('focus', () => document.getElementById('UserRealNameSearchedField').classList.remove('d-hidden'));

    userRealNameSearch.addEventListener('blur', () => {
        wait(100)
        .then(() => document.getElementById('UserRealNameSearchedField').classList.add('d-hidden'));
    });

    userRealNameSearch.addEventListener('input', () => {
        if(userRealNameSearch.value !== ''){

            let body = JSON.stringify({
                username : userRealNameSearch.value
            });
            httpRequest(`/admin/getIncludeUserRealNameList`, 'POST', body)
            .then(response => {
                if(response.ok){
                    return response.json();
                }
            })
            .then(data => {
                removeAllChildNodes(document.getElementById('UserRealNameSearchedField'));
                data.forEach(result => {
                    let fullText = document.createElement('div');
                    fullText.setAttribute('class', 'row w-100 mx-auto ps-2 userNameField');
                    fullText.setAttribute('onclick', 'getUserName(this)');

                    let frontText = document.createElement('div');
                    frontText.setAttribute('class', 'col-auto p-0');
                    frontText.textContent = result.frontText;

                    let searchText = document.createElement('div');
                    searchText.setAttribute('class', 'col-auto p-0 color-blue');
                    searchText.textContent = result.searchText;

                    let endText = document.createElement('div');
                    endText.setAttribute('class', 'col-auto p-0');
                    endText.textContent = result.endText;

                    let realNameText = document.createElement('div');
                    realNameText.setAttribute('class', 'col-auto p-0');
                    realNameText.textContent = result.realName;

                    document.getElementById('UserRealNameSearchedField').appendChild(fullText);
                    fullText.appendChild(frontText);
                    fullText.appendChild(searchText);
                    fullText.appendChild(endText);
                    fullText.appendChild(realNameText);
                });
            });

        }
        else{
            removeAllChildNodes(document.getElementById('UserRealNameSearchedField'));
        }
    });
}

const searchButton = document.getElementById('search-btn');

if(searchButton){
    searchButton.addEventListener('click', () => {
        let param = '';
        param += document.getElementById('productNameSearch').value !== '' ? 'productName=' + document.getElementById('productNameSearch').value  + '&' : '';
        param += document.getElementById('userRealNameSearch').value !== '' ? 'username=' + document.getElementById('userRealNameSearch').value  + '&' : '';
        param += 'startDate=' + document.getElementById('startYearSel').value + document.getElementById('startMonthSel').value + document.getElementById('startDaySel').value + '&';
        param += 'endDate=' + document.getElementById('endYearSel').value + document.getElementById('endMonthSel').value + document.getElementById('endDaySel').value + '&';
        param += document.getElementById('minPrice').value !== '' ? 'minPrice=' + document.getElementById('minPrice').value  + '&' : '';
        param += document.getElementById('maxPrice').value !== '' ? 'maxPrice=' + document.getElementById('maxPrice').value  + '&' : '';
        param += document.getElementById('paymentStatusSel').value !== '' ? 'paymentStatus=' + document.getElementById('paymentStatusSel').value  + '&' : '';
        param += document.getElementById('paymentDetailStatusSel').value !== '' ? 'paymentDetailStatus=' + document.getElementById('paymentDetailStatusSel').value  + '&' : '';

        location.replace('/admin/orderManager?' + param); //로그를 안남김(뒤로가기 불가);
//        location.href='/admin/orderManager?' + param;
    });
}

const detailViewButton = document.getElementsByClassName('selectable');

if(detailViewButton){
    Array.from(detailViewButton).forEach(button => {
        button.addEventListener('click', () => location.href = '/admin/orderDetailManager/' + button.previousElementSibling.value);
    });
}

//체크박스 설정
const AllSelectCheckBox = document.getElementById('allSelectCheckBox');

if(AllSelectCheckBox){
    AllSelectCheckBox.addEventListener('click', () => {
        if(AllSelectCheckBox.checked){
            Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox')).forEach(checkBox => {
                if(!checkBox.checked){
                    checkBox.checked = true;
                }
            });
        }
        else{
            Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox')).forEach(checkBox => {
                if(checkBox.checked){
                    checkBox.checked = false;
                }
            });
        }

    });
}

//선택 상품 상태변경 버튼
const selectedStatusChangeButton = document.getElementById('selectedStatusChange-btn');

if(selectedStatusChangeButton){
    selectedStatusChangeButton.addEventListener('click', () => {
        if(toCheckValueList(Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox'))).length === 0){
            alert('항목을 선택하세요.');
            return;
        }

        if(document.getElementById('statusSel').value !== '선택'){
            let body = JSON.stringify({
                paymentIds : toCheckValueList(Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox'))),
                paymentStatus : document.getElementById('statusSel').value
            });

            httpRequest(`/admin/orderManager`, 'POST', body)
            .then(response => {
                if(response.ok){
                    return response.json();
                }
            })
            .then(data => {
                console.log(data);
                data.forEach(paymentDetail => document.getElementById('paymentId' + paymentDetail.paymentId).nextElementSibling.children[5].textContent = paymentDetail.paymentStatus)
                alert('변경사항이 적용되었습니다.');
            });
        }
        else{
            alert('상태를 선택해주세요.');
        }

    });
}

//이전 페이지 버튼
const prevPageButton = document.getElementById('prevPage-btn');

if(prevPageButton){
    prevPageButton.addEventListener('click', () => {
        //원래 페이지의 체크박스 모두 해제
        Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox')).forEach(checkBox => checkBox.checked = false);
        document.getElementById('allSelectCheckBox').checked = false;

        //각 페이지 버튼 상태 변경
        let selectedPageButton = document.getElementById('selectedPage-btn');
        selectedPageButton.setAttribute('id', '');
        selectedPageButton.previousElementSibling.setAttribute('id', 'selectedPage-btn');

        //페이지 보여주기
        let currentPage = document.getElementById('selectedPage');
        currentPage.classList.add('d-hidden');
        currentPage.setAttribute('id', '');
        currentPage.previousElementSibling.setAttribute('id', 'selectedPage');
        document.getElementById('selectedPage').classList.remove('d-hidden');

        if(document.getElementById('selectedPage').previousElementSibling === null){
            prevPageButton.setAttribute('disabled', '');
        }
        else{
            prevPageButton.removeAttribute('disabled');
        }

        if(document.getElementById('selectedPage').nextElementSibling === null){
            nextPageButton.setAttribute('disabled', '');
        }
        else{
            nextPageButton.removeAttribute('disabled');
        }
    });
}

const nextPageButton = document.getElementById('nextPage-btn');

if(nextPageButton){
    nextPageButton.addEventListener('click', () => {
        //현제 페이지의 체크박스 모두 해제
        Array.from(document.getElementById('selectedPage').getElementsByClassName('itemCheckBox')).forEach(checkBox => checkBox.checked = false);
        document.getElementById('allSelectCheckBox').checked = false;

        //각 페이지 버튼 상태 변경
        let selectedPageButton = document.getElementById('selectedPage-btn');
        selectedPageButton.setAttribute('id', '');
        selectedPageButton.nextElementSibling.setAttribute('id', 'selectedPage-btn');

        //페이지 보여주기
        let currentPage = document.getElementById('selectedPage');
        currentPage.classList.add('d-hidden');
        currentPage.setAttribute('id', '');
        currentPage.nextElementSibling.setAttribute('id', 'selectedPage');
        document.getElementById('selectedPage').classList.remove('d-hidden');

        if(document.getElementById('selectedPage').previousElementSibling === null){
            prevPageButton.setAttribute('disabled', '');
        }
        else{
            prevPageButton.removeAttribute('disabled');
        }

        if(document.getElementById('selectedPage').nextElementSibling === null){
            nextPageButton.setAttribute('disabled', '');
        }
        else{
            nextPageButton.removeAttribute('disabled');
        }

    });
}



//페이지 로딩시 초기값
if(document.getElementById('currentYear')){
    let currentYear = parseInt(document.getElementById('currentYear').value);
    for(let i = 0; i < 11; i++){
        let comp = document.createElement('option');
        comp.textContent = currentYear - i;
        if(currentYear - i === parseInt(document.getElementById('startYear').value)){
            comp.setAttribute('selected', '');
        }
        document.getElementById('startYearSel').appendChild(comp);

        let comp2 = document.createElement('option');
        if(currentYear - i === parseInt(document.getElementById('endYear').value)){
            comp2.setAttribute('selected', '');
        }
        comp2.textContent = currentYear - i;
        document.getElementById('endYearSel').appendChild(comp2);
    }
    //시작 달,일 설정
    document.getElementById('startMonthSel').children[parseInt(document.getElementById('startMonth').value) - 1].setAttribute('selected', '');
    document.getElementById('startDaySel').children[parseInt(document.getElementById('startDay').value) - 1].setAttribute('selected', '');
    //끝 달,일 설정
    document.getElementById('endMonthSel').children[parseInt(document.getElementById('endMonth').value) - 1].setAttribute('selected', '');
    document.getElementById('endDaySel').children[parseInt(document.getElementById('endDay').value) - 1].setAttribute('selected', '');
}

//넘버링
if(document.getElementsByClassName('number')){
    Array.from(document.getElementsByClassName('number')).forEach((comp, index) => comp.textContent = index + 1);
}

//첫 페이지 보이기
const itemField = document.getElementById('itemField');

if(itemField.children[0] != null){
    itemField.children[0].classList.remove('d-hidden');
    itemField.children[0].setAttribute('id', 'selectedPage');
}

//페이지 수량 확인
document.getElementById('pageSize').value = itemField.childElementCount;
if(parseInt(document.getElementById('pageSize').value) > 0){
    let size = parseInt(document.getElementById('pageSize').value);
    for(let i = 0; i < (size > 5 ? 5 : size); i++){

        let comp = document.createElement('div');
        comp.setAttribute('class', 'col-auto cursor');
        comp.setAttribute('style', 'width:40px;');
        comp.setAttribute('onclick', 'movePage(this)');
        comp.textContent = i + 1;

        document.getElementById('pageSelectField').appendChild(comp);
        console.log(i + 1);
    }

    document.getElementById('pageSelectField').children[0].setAttribute('id', 'selectedPage-btn');
    prevPageButton.setAttribute('disabled', '');
    if(document.getElementById('pageSize').value === '1'){
        nextPageButton.setAttribute('disabled', '');
    }
}
else{
    document.getElementById('pageBar').remove();
}


//초기값 설정
//원화 변경
if(document.getElementsByClassName('price')){

    Array.from(document.getElementsByClassName('price')).forEach(comp => {

        console.log('indexOf : ' + comp.textContent.indexOf(' '));
        if(comp.textContent.indexOf(' ') === -1){
            comp.textContent = toWon(comp.textContent)
        }
        else{
            comp.textContent = toWon(comp.textContent.substring(0, comp.textContent.indexOf(' '))) + comp.textContent.substring(comp.textContent.indexOf(' '));
        }
    });
}

let text = "2676469 (16건)";

console.log(text.indexOf(' '));
console.log(text.substring(text.indexOf(' ')));
console.log(toWon(text.substring(0, text.indexOf(' '))));
console.log(text.indexOf(' ') === -1);
