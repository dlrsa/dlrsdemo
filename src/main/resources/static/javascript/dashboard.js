$(document).ready(function () {
    $('.team-button').click(function (e) {
        e.preventDefault();
        var teamId = $(this).find('.teamcode').val();
//        alert(teamId);
        window.location.href = "/secure/teamDetails/" + teamId;


//         $.ajax({
//             url: '/secure/getTeamData',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify({ teamId: teamId }), // Send JSON data
//             success: function (data) {
//                 alert(data);
//             },
//             error: function (jqXHR, textStatus, errorThrown) {
//                 console.error('Error:', textStatus);
//             }
//         });
    });
});

