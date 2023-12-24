$('[data-bs-target="#newGroupModal"]').on("click", (e) => {

    $('#newGroupModal').find('[name="name"]').val("")
    $('#newGroupModal').find('[name="subText"]').val("")
    $('#newGroupModal').find('#transl_group_key_error').html("")
    $('#newGroupModal').find('#transl_group_title_error').html("")

    $('#newGroupModal').modal('show')
})


$('#add_new_group_form').on("submit", (e) => {
    e.preventDefault()  
    let data = $(e.target).serialize()
    let url = $(e.target).attr('action')

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        datatype: 'json',
        success: (data) => {
            $('#group_links').html(
                $('#group_links').html() + 
                `
                    <a href="/admin/translations/groups/${ data.object.id }" class="btn btn-info me-3 bg-transparent text-info group-link">${ data.object.name }</a>
                `
            )

            $(e.target).find('[name="title"]').val("")
            $(e.target).find('[name="subText"]').val("")
            $('#newGroupModal').modal("hide")
        },
        error: (xhr, status, error) => {
            if(Object.keys(xhr.responseJSON).includes("error")) {
                $('#newGroupModal').find('#transl_group_key_error').css("display", "block")
                $('#newGroupModal').find('#transl_group_key_error').text(xhr.responseJSON['error'])
            }
        }
    })
})