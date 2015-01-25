$(document).ready(function () {
    //bind event handlers
    $('.mail-list').on('click', mailListOnClick);
});

function mailListOnClick(e) {
    e.preventDefault();
    //change list highlighting
    $('.active').removeClass('active');
    $(e.target).addClass('active');
    //load the mail
    $.get(e.target.href +  document.location.search) //preserving auth token from referrer
        .success(function(data) {
            $('#mail-body').html(data.mail_body.split('\n').join('<br>'));
            $('#mail-body-raw').html(data.mail_body_raw.split('\n').join('<br>'));
            $('#sender').text(data.envelope_from);
            $('#recipients').text(data.envelope_recipients);
            $('#date').text(data.delivery_date);
            $('#ip').text(data.sender_ip);
        })
        .error(function () {
            // todo: replace html insertion with DOM operations
            var html = '<div class="alert alert-danger alert-dismissable">Message you are referring to doesn\'t exist. It may be evicted from the mailbox due to rotation. Try to reload the page to update the list of available mails.</div>'
            $("#error-container").html(html)
        })
}