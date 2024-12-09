$(document).ready(function () {
    $(document).on('click', '.delete-btn', function () {
        alert("Here");
//        var userId = $(this).data('id');
        var userId = $("#userid").val();

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
                    if (response.toString() === "success") {
                        alert("User deleted successfully!");
                        location.reload();
                       } else if (response.toString() === "conflict") {
                                               alert("Unable to delete! 😘 User already in team");
                                               location.reload();
                                           }
                    } else {
                        alert("Unable to delete User!");
                        location.reload();
                    }
                },
                error: function () {
                    alert("Failed to delete the user.");
                }
            });
        } else {
            console.log("Delete canceled");
        }
    });
});
