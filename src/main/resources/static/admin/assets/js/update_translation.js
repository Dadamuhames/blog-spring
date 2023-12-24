$(document).on('click', '.translation-update-btn', (e) => {
    let id = $(e.target).attr("data-id")

    let url = `/admin/translations/${id}/`

    $.ajax({
        url: url,
        type: "GET",
        datatype: 'json',
        success: (data) => {
            console.log(data)
            let form = $('#translation-update-form')
            $(form).find("#transl_lng_error").html('')
            $(form).find('#transl_key_error').html('')


            $('#exampleModalLabelOne').html(`${data?.group?.subText}.${data?.keyword}`)
            $(form).find("[name='obj_id']").val(data?.id)
            $(form).find("[name='group_id']").val(data?.group?.id)
            $(form).find('#group-key-name').html(data?.group?.subText + '.')
            $(form).find("[name='keyword']").val(data?.keyword)
            for(let key in data?.value) {
                $(form).find(`[name='value[${key}]']`).val(data?.value[String(key)])
            }
        }, 
        error: (xhr) => {
            if (Object.keys(xhr.responseJSON).includes("error")) {
                console.log(xhr.responseJSON?.error);
            }
        }
    })

})


$(document).on('submit', '#translation-update-form', (e) => {
    e.preventDefault()
    let data = $(e.target).serialize()

    console.log(data)

    let id = $(e.target).find("[name='obj_id']").val();

    url = `/admin/translations/${id}`;


    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        datatype: 'json',
        success: (data) => {
//            console.log(data)
            window.location.reload()
        },
        error: (xhr) => {
            $('#transl_key_error').html(xhr.responseJSON?.errors?.keyword)
            $('#transl_lng_error').html(xhr.responseJSON?.errors?.value)
        }
    })

})