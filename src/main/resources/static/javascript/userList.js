$(document).ready(function () {
    $(document).on('click', '.delete-btn', function () {
//        var userId = $(this).data('id');
        var userId = $(this).closest('.dropdown-menu').find('.userid').val();

        alert(userId);

        console.log("user Id" + userId);
        if (confirm("Are you sure you want to delete this user?")) {
            // Proceed with delete operation (send request to server)
            console.log("User ID to delete:", userId);

            // Example: Send an AJAX request to delete the user
            $.ajax({
                url: '/secure/deleteUser', // Replace with your endpoint
                type: 'DELETE',
                 data: {
                        userId: userId
                  },
                success: function (response) {
                    alert(response);
                    location.reload();
                },
                error: function () {
                    alert("Failed to delete the user");
                }
            });
        } else {
            console.log("Delete canceled");
        }
    });
});
