$(document).ready(function () {

    // get valid msisdns against logged in user
    const params = new URL(window.location).searchParams;
    let username = params.get('username');
    let active_numbers_api = "http://10.192.224.18:8882/nc/numberpool/getAllActiveNumbersForUsername?username="+username;
    let active_numbers = [];
    $.ajax({
        url: active_numbers_api,
        type: "GET",
        success: (response => {
            response.map((item)=>{
                let option = "<option value="+item+">"+item+"</option>";
                $('#Msisdn').append(option);
            });
        }),
        error:(response)=>{
            console.log(response);
            active_numbers = [];
        }
    });
    // let date = new Date();
    // let formatted_date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    $('#onboarding_form').on('submit', (e) => {
        $('#exampleModalCenter').modal('show');
        e.preventDefault();
        let SimType = $('#SimType').val();
        let DealerId = $('#DealerId').val();
        let Location = $('#Location').val();
        let GenerateSAF = $('#GenerateSAF').prop('checked') ? "Y" : "N";
        let DigitalySigned = $('#DigitalySigned').prop('checked') ? "Y" : "N";
        let TransactionId = $('#TransactionId').val();
        let Msisdn = $('#Msisdn').val();
        let Title = $('#Title').val();
        let FirstName = $('#FirstName').val();
        let LastName = $('#LastName').val();
        let VisaType = $('#VisaType').val();
        let IdNumber = $('#IdNumber').val();
        let IdExpirationDate = $('#IdExpirationDate').val();
        let Language = $('#Language').val();
        let Nationality = $('#Nationality').val();
        let EmailAddress = $('#EmailAddress').val();
        let inputAddress = $('#inputAddress').val();
        let PromoID = $('#PromoID').val();
        let IdNRatePlanIdumber = $('#RatePlanId').val();
        let RefNo = 'REF NUMBER';
        let sim_only_url = "http://10.192.224.18:9791/api/onboarding/simonly";
        let reserve_num_url = "http://10.192.224.18:9791/api/nms/reservenumber";

        let reserve_num_data = {
            "accountid": "WEBPORTAL",
            "accounttype": "Residential",
            "msisdn": Msisdn,
            "effectivedate": new Date(),
            "reservationstatus": "R",
            "loginid": "WEBPORTAL",
            "dealerlocatorid": Location,
            "actualdealerid": DealerId
        }

        let sim_only_data =
        {
            "channel": "WEBPORTAL",
            "simType": SimType,
            "dealerId": DealerId,
            "dealerLocation": Location,
            "userType": "POSTPAID",
            "generateSAF": GenerateSAF,
            "digitallySigned": DigitalySigned,
            "transactionId": TransactionId,
            "msisdn": Msisdn,
            "title": Title,
            "firstName": FirstName,
            "lastName": LastName,
            "visaType": VisaType,
            "idNumber": IdNumber,
            "idExpiryDate": IdExpirationDate,
            "language": Language,
            "nationality": Nationality,
            "email": EmailAddress,
            "promoId": PromoID,
            "rateplanId": IdNRatePlanIdumber,
            "agentName": "",
            "agentId": ""
        }
        $.ajax({
            url: reserve_num_url,
            type: "POST",
            dataType: "json",
            data: JSON.stringify(reserve_num_data),
            contentType: "application/json",
            accepts: 'application/json',
            success: (response => {
                if (response.numberReservaionBeanOut.status !== 'FAILED') {
                    $.ajax({
                        url: sim_only_url,
                        type: "POST",
                        dataType: "json",
                        data: JSON.stringify(sim_only_data),
                        contentType: "application/json",
                        accepts: 'application/json',
                        success: (response => {
                            $('#progressModal').modal('hide');
                            if (response.responseCode === "0") {
                                alert(response.responseMessage);
                            }
                        }),
                        error: (error) => {
                            console.log(error);
                            $('#progressModal').modal('hide');
                            alert('Unable to Reserve the Number');
                        }
                    });
                } else {
                    $('#progressModal').modal('hide');
                    alert('Unable to Reserve the Number');
                }
            }),
            error: (error) => {
                console.log(error);
                $('#progressModal').modal('hide');
            }
        });
    })
});