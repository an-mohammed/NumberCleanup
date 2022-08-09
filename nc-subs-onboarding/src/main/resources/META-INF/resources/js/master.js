$(document).ready(function () {
    $("#TransactionId").val(genTransId());
    $("#Msisdn").select2();
    const params = new URL(window.location).searchParams;
    let username = params.get('username');
    let active_numbers_api = "http://10.192.224.18:8882/nc/numberpool/getAllActiveNumbersForUsername?username=" + username;
    let active_numbers = [];
    let promo_id = "NULL";
    $.ajax({
        url: active_numbers_api,
        type: "GET",
        success: (response => {
            response.map((item) => {
                let option = "<option value=" + item + ">" + item + "</option>";
                $('#Msisdn').append(option);
            });
        }),
        error: (response) => {
            console.log(response);
            active_numbers = [];
        }
    });

    $('#tab-b2c-postpaid').on('shown.bs.tab', (tab) => {
        $('.card-title').text('B2C Postpaid Subscriber Creation');
        $("#RatePlanId").val();
    });
    $('#tab-b2c-prepaid').on('shown.bs.tab', (tab) => {
        $('.card-title').text('B2C Prepaid Subscriber Creation');
        $("#RatePlanId").val();
    });
    $('#tab-ana').on('shown.bs.tab', (tab) => {
        $('.card-title').text('ANA Subscriber Creation');
        $("#RatePlanId").val();
    });
    $('#tab-b2b-epr').on('shown.bs.tab', (tab) => {
        $('.card-title').text('B2B EPR Subscriber Creation');
        $("#RatePlanId").val();
    });
    $('#tab-b2b-cpr').on('shown.bs.tab', (tab) => {
        $('.card-title').text('B2B CPR Subscriber Creation');
        $("#RatePlanId").val();
    });
    $("#b2c-postpaid #PromoID").on('change', () => {
        $("#RatePlanId").val($("#b2c-postpaid #PromoID").val());
	promo_id = $("#b2c-postpaid #PromoID").val();
    });
    $("#b2c-prepaid #PromoID").on('change', () => {
        $("#RatePlanId").val($("#b2c-prepaid #PromoID").val());
        promo_id = $("#b2c-prepaid #PromoID").val();
    });
    $("#ana #PromoID").on('change', () => {
        $("#RatePlanId").val($("#ana #PromoID").val());
        promo_id = $("#ana #PromoID").val();
    });
    $("#b2b-epr #PromoID").on('change', () => {
        $("#RatePlanId").val($("#b2b-epr #PromoID").val());
        promo_id = $("#b2b-epr #PromoID").val();
    });
    $("#b2b-cpr #PromoID").on('change', () => {
        $("#RatePlanId").val($("#b2b-cpr #PromoID").val());
        promo_id = $("#b2b-cpr #PromoID").val();
    });

    $("#subscriber-creation-form").on('submit', (e) => {
        e.preventDefault();
        var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), { backdrop: 'static' });
        myModal.show();
        let line_type = $(e.originalEvent.submitter).attr('id');
        let line = "";
        switch (line_type) {
            case "submit-b2c-postpaid":
                line = "POSTPAID";
                break;
            case "submit-b2c-prepaid":
                line = "PREPAID";
                break;
        }
        $('.progress').show();
        $('.failed').addClass('hidden');
        $('.success').addClass('hidden');
        $("#request-title").text('Please wait while request is processing..');

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
        let date = new Date($('#IdExpirationDate').val());
        let IdExpirationDate = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
        let Language = $('#Language').val();
        let Nationality = $('#Nationality').val();
        let EmailAddress = $('#EmailAddress').val();
        let inputAddress = $('#inputAddress').val();
        let PromoID = promo_id;
        let IdNRatePlanIdumber = $('#RatePlanId').val();
        let simNo = $('#SimNo').val();
        let RefNo = 'REF NUMBER';
        let sim_only_url = "http://10.192.224.18:9791/api/onboarding/simonly";
        let reserve_num_url = "http://10.192.224.18:9791/api/nms/reservenumber";

        if (PromoID === 'NULL') {
            $('#modal_close').removeAttr('disabled');
            $('.success').addClass('hidden');
            $("#request-title").text("Please Choose Promo");
            $('.progress').hide();
        } else {
            let request_data = {
                salutation: Title,
                firstName: FirstName,
                lastName: LastName,
                email: EmailAddress,
                msisdn: Msisdn,
                simNo: simNo,
                idType: VisaType,
                idValue: IdNumber,
                idExpirationDate: IdExpirationDate,
                visaType: VisaType,
                transactionNo: TransactionId,
                dealerId: DealerId,
                promoId: PromoID,
                language: Language,
                nationality: Nationality,
                subscriberType: line
            };
            $.ajax({
                url: 'http://10.192.224.18:8882/nc/subscriber/createSubscriberProfile',
                type: "POST",
                data: JSON.stringify(request_data),
                dataType: "json",
                contentType: "application/json",
                success: (response) => {
                    $('.progress').hide();
                    $("#request-title").text('Subscriber Onboarded successfully');
                    $('.failed').addClass('hidden');
                    $('.success').removeClass('hidden');
                    $('.spcId').text(response.spcId);
                },
                error: (error) => {
                    $('#modal_close').removeAttr('disabled');
                    try {
                        let result = JSON.parse(error.responseText);
                        $('.success').addClass('hidden');
                        $("#request-title").text(result.error);
                        $('.failed').removeClass('hidden');
                        $('.failed').text(result.message);
                        $('.progress').hide();
                    } catch (e) {
                        $("#request-title").text(error.responseText);
                        $('.progress').hide();
                    }

                }
            });
        }
    });
});
function genTransId() {
    var date = new Date();
    var components = [
        date.getFullYear(),
        date.getMonth(),
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
        date.getMilliseconds()
    ];
    return components.join("");
}