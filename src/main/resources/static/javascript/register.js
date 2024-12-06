$(document).ready(function () {
    $('#btnclick').click(function (e) {
        e.preventDefault();

        const form = document.getElementById('register');
        const formData = new FormData(form);

        // Convert FormData to a plain JavaScript object
        const dataObject = {};
        formData.forEach((value, key) => {
            dataObject[key] = value;
        });

        // Convert the plain object to a JSON string
        const jsonData = JSON.stringify(dataObject);

        console.log("Appeal Form Data Object - ", dataObject);
        console.log("Appeal Form JSON - ", jsonData);

        $.ajax({
            url: '/secure/createUser',
            type: 'POST',
            contentType: 'application/json',
            data: jsonData, // Send JSON data
            success: function (data) {
                alert(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 400) {
                    $.alert({
                        title: 'Error!',
                        content: "Username or password is incorrect",
                        type: 'red'
                    });
                } else if (jqXHR.status === 409) {
                    // Handle conflict (e.g., username already exists)
                } else {
                    $.alert({
                        title: 'Error!',
                        content: 'An error occurred: ' + textStatus,
                        type: 'red'
                    });
                }
            }
        });
    });
});
