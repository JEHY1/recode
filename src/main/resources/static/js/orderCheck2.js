function httpRequest(url, method, body){
    return fetch(url, {
        method: method,
        headers: {
            "Content-Type" : "application/json"
        },
        body: body
    });
}

const viewButton = document.getElementById('view-btn');

if(viewButton){
    viewButton.addEventListener('click', () => {
        let startDate = parseInt(document.getElementById('startYear').value + document.getElementById('startMonth').value + document.getElementById('startDay').value);
        let endDate = parseInt(document.getElementById('endYear').value + document.getElementById('endMonth').value + document.getElementById('endDay').value);
        let currentDate = parseInt(document.getElementById('currentYear').value + document.getElementById('currentMonthAndDay').value);
        if(currentDate < startDate){
            alert('검색시작일을 오늘 이전으로 선택하세요.');
            return;
        }
        if(currentDate < endDate){
            alert('검색종료일을 오늘 이전으로 선택하세요');
            return;
        }
        if(endDate < startDate){
            alert('검색 종료일이 검색 시작일 이전입니다.');
            return;
        }

        location.replace("/user/orderCheck?startDate=" + document.getElementById('startYear').value + document.getElementById('startMonth').value + document.getElementById('startDay').value + "&endDate=" + document.getElementById('endYear').value + document.getElementById('endMonth').value + document.getElementById('endDay').value);
    });
}

if(document.getElementById('currentYear')){

    let currentYear = parseInt(document.getElementById('currentYear').value);

    //페이지 로드시 날짜 설정창이 검색한 날짜년도로 설정
    for(let i = 0; i < 10; i++){
        let yearOption1 = document.createElement('option');
        let yearOption2 = document.createElement('option');
        yearOption1.textContent = currentYear - i;
        yearOption2.textContent = currentYear - i;
        if(parseInt(document.getElementById('searchedStartYear').value) === currentYear - i){
            yearOption1.setAttribute('selected', '');
        }
        if(parseInt(document.getElementById('searchedEndYear').value) === currentYear - i){
            yearOption2.setAttribute('selected', '');
        }

        document.getElementById('startYear').appendChild(yearOption1);
        document.getElementById('endYear').appendChild(yearOption2);
    }

    //시작 달 설정
    document.getElementById('startMonth').children[parseInt(document.getElementById('searchedStartMonth').value) - 1].setAttribute('selected', '');
    //달에 따른 일수 변경
    let searchedStartMonth = parseInt(document.getElementById('searchedStartMonth').value);
    if(searchedStartMonth === 2){
        document.getElementById('startDay').children[30].classList.add('d-hidden');
        document.getElementById('startDay').children[29].classList.add('d-hidden');
        let year = parseInt(document.getElementById('searchedStartYear').value);
        if(!(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))){
            document.getElementById('startDay').children[28].classList.add('d-hidden');
        }
    }
    else if(searchedStartMonth === 4 || searchedStartMonth === 6 || searchedStartMonth === 9 || searchedStartMonth === 11){
        document.getElementById('startDay').children[30].classList.add('d-hidden');
    }


    //끝나는 달 설정
    document.getElementById('endMonth').children[parseInt(document.getElementById('searchedEndMonth').value) - 1].setAttribute('selected', '');
    //달에 따른 일수 변경
    let searchedEndMonth = parseInt(document.getElementById('searchedEndMonth').value);
    if(searchedEndMonth === 2){
        document.getElementById('endDay').children[30].classList.add('d-hidden');
        document.getElementById('endDay').children[29].classList.add('d-hidden');
        let year = parseInt(document.getElementById('searchedEndMonth').value);
        if(!(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))){
            document.getElementById('endDay').children[28].classList.add('d-hidden');
        }
    }
    else if(searchedEndMonth === 4 || searchedEndMonth === 6 || searchedEndMonth === 9 || searchedEndMonth === 11){
        document.getElementById('endDay').children[30].classList.add('d-hidden');
    }

    //일 설정
    document.getElementById('startDay').children[parseInt(document.getElementById('searchedStartDay').value) - 1]. setAttribute('selected', '');
    document.getElementById('endDay').children[parseInt(document.getElementById('searchedEndDay').value) - 1]. setAttribute('selected', '');


    //시작 달 선택시 시작일 선택가능 범위 변경
    let startMonthSelection = document.getElementById('startMonth');
    let startYearSelection = document.getElementById('startYear');

    startMonthSelection.addEventListener('change', () => {
        console.log('change');
        let startMonth = parseInt(startMonthSelection.value);
        let startYear = parseInt(startYearSelection.value);
        console.log(startMonth);
        console.log(startYear);

        if(startMonth === 2){
            document.getElementById('startDay').children[30].classList.add('d-hidden');
            document.getElementById('startDay').children[29].classList.add('d-hidden');
            if(!(startYear % 4 == 0 && (startYear % 100 != 0 || startYear % 400 == 0))){
                document.getElementById('startDay').children[28].classList.add('d-hidden');
                //2월 선택시 날짜가 29일 이상일 경우 날짜를 28일로 변경
                if(parseInt(document.getElementById('startDay').value) > 28){
                    document.getElementById('startDay').value = 28;
                }
            }
            else{
                //윤년일 경우 29일로 변경
                if(parseInt(document.getElementById('startDay').value) > 29){
                    document.getElementById('startDay').value = 29;
                }
            }


        }
        else if(startMonth === 4 || startMonth === 6 || startMonth === 9 || startMonth === 11){
            document.getElementById('startDay').children[30].classList.add('d-hidden');
            document.getElementById('startDay').children[29].classList.remove('d-hidden');
            document.getElementById('startDay').children[28].classList.remove('d-hidden');

            //30일 까지 있는 달 선택시 31일로 선택되어 있을경우 30일로 변경
            if(parseInt(document.getElementById('startDay').value) === 31){
                document.getElementById('startDay').value = 30;
            }
        }
        else{
            document.getElementById('startDay').children[30].classList.remove('d-hidden');
            document.getElementById('startDay').children[29].classList.remove('d-hidden');
            document.getElementById('startDay').children[28].classList.remove('d-hidden');
        }
    });

    //끝나는 달 선택시 시작일 선택가능 범위 변경
    let endMonthSelection = document.getElementById('endMonth');
    let endYearSelection = document.getElementById('endYear');

    endMonthSelection.addEventListener('change', () => {
        console.log('change');
        let endMonth = parseInt(endMonthSelection.value);
        let endYear = parseInt(endYearSelection.value);

        if(endMonth === 2){
            document.getElementById('endDay').children[30].classList.add('d-hidden');
            document.getElementById('endDay').children[29].classList.add('d-hidden');
            if(!(endYear % 4 == 0 && (endYear % 100 != 0 || endYear % 400 == 0))){
                document.getElementById('endDay').children[28].classList.add('d-hidden');
                //2월 선택시 날짜가 29일 이상일 경우 날짜를 28일로 변경
                if(parseInt(document.getElementById('endDay').value) > 28){
                    document.getElementById('endDay').value = 28;
                }
            }
            else{
                //윤년일 경우 29일로 변경
                if(parseInt(document.getElementById('endDay').value) > 29){
                    document.getElementById('endDay').value = 29;
                }
            }
        }
        else if(endMonth === 4 || endMonth === 6 || endMonth === 9 || endMonth === 11){
            document.getElementById('endDay').children[30].classList.add('d-hidden');
            document.getElementById('endDay').children[29].classList.remove('d-hidden');
            document.getElementById('endDay').children[28].classList.remove('d-hidden');

            //30일 까지 있는 달 선택시 31일로 선택되어 있을경우 30일로 변경
            if(parseInt(document.getElementById('endDay').value) === 31){
                document.getElementById('endDay').value = 30;
            }
        }
        else{
            document.getElementById('endDay').children[30].classList.remove('d-hidden');
            document.getElementById('endDay').children[29].classList.remove('d-hidden');
            document.getElementById('endDay').children[28].classList.remove('d-hidden');
        }
    });

    startYearSelection.addEventListener('change', () => {

        let startYear = parseInt(startYearSelection.value);
        //2월달이 선택되어 있던 경우
        if(startMonthSelection.value === '02'){
            //윤년일 경우
           if(startYear % 4 == 0 && (startYear % 100 != 0 || startYear % 400 == 0)){
                document.getElementById('startDay').children[28].classList.remove('d-hidden');
           }
           else{
                document.getElementById('startDay').children[28].classList.add('d-hidden');
                //29일 선택되어 있을 경우 28로 변경
                if(document.getElementById('startDay').value === '29'){
                    document.getElementById('startDay').value = 28;
                }
           }
        }
    });

    endYearSelection.addEventListener('change', () => {

        let endYear = parseInt(endYearSelection.value);
        //2월달이 선택되어 있던 경우
        if(endMonthSelection.value === '02'){
            //윤년일 경우
           if(endYear % 4 == 0 && (endYear % 100 != 0 || endYear % 400 == 0)){
                document.getElementById('endDay').children[28].classList.remove('d-hidden');
           }
           else{
                document.getElementById('endDay').children[28].classList.add('d-hidden');
                //29일 선택되어 있을 경우 28로 변경
                if(document.getElementById('endDay').value === '29'){
                    document.getElementById('endDay').value = 28;
                }
           }
        }
    });
}

const orderManageButtons = document.getElementsByClassName('orderManage-btn');

if(orderManageButtons){
    Array.from(orderManageButtons).forEach(button => {
        button.addEventListener('click', () => {
            console.log('click');
            let body = JSON.stringify({
                paymentDetailId : button.previousElementSibling.value,
                paymentDetailStatus : button.previousElementSibling.previousElementSibling.textContent,
                paymentDetailStatusRequest : button.textContent
            });

            httpRequest(`/user/orderManage`, 'POST', body)
            .then(response => {
                if(response.ok){
                    alert('요청사항이 반영되었습니다.')
                }
                else{
                    alert('error');
                }
            })

        });
    });
}
