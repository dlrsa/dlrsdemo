$(document).ready(function() {
    // Trigger when the district dropdown value changes
    $('#district').change(function() {
        // Get the selected district value
        var districtCode = $(this).val();

        // Make an AJAX GET request to fetch subdivisions based on district
        $.ajax({
            url: '/secure/getSubdivisions', // Update this URL to match your API endpoint
            type: "GET",
            data: { districtCode: districtCode }, // Send the districtCode as a query parameter
            success: function(data) {
                console.log("Data for sub service array", data);

                // Clear the existing options in the #subdiv dropdown
                $('#subdiv').empty();

                // Add a default "Select" option
                $('#subdiv').append('<option value="">Select</option>');

                // Check if data has been returned, assuming it's an array of subdivisions
                if (data && data.length > 0) {
                    // Iterate over the data array and create options dynamically
                    data.forEach(function(subdivision) {
                        $('#subdiv').append('<option value="' + subdivision.subdiv_code + '">' + subdivision.loc_name + '</option>');
                    });
                } else {
                    // In case no data is returned, you can show a message or leave empty
                    $('#subdiv').append('<option value="">No subdivisions available</option>');
                }
            },
            error: function(error) {
                console.log('Error:', error);
                // Optionally, you can show an error message to the user
                $('#subdiv').empty();
                $('#subdiv').append('<option value="">Failed to load subdivisions</option>');
            }
        });
    });

    // Trigger when the subdivision dropdown value changes
    $('#subdiv').change(function() {
        // Get the selected subdivision code
        var subdivCode = $(this).val();

        // If subdivision is selected, make an AJAX GET request to fetch circles based on subdivision
        if (subdivCode) {
            $.ajax({
                url: '/secure/getCircles', // Update this URL to match your API endpoint
                type: "GET",
                data: { subdivCode: subdivCode }, // Send the subdivCode as a query parameter
                success: function(data) {
                    console.log("Data for circles array", data);

                    // Clear the existing options in the #circle dropdown
                    $('#circle').empty();

                    // Add a default "Select" option
                    $('#circle').append('<option value="">Select</option>');

                    // Check if data has been returned, assuming it's an array of circles
                    if (data && data.length > 0) {
                        // Iterate over the data array and create options dynamically
                        data.forEach(function(circle) {
                            $('#circle').append('<option value="' + circle.cir_code + '">' + circle.loc_name + '</option>');
                        });
                    } else {
                        // In case no data is returned, you can show a message or leave empty
                        $('#circle').append('<option value="">No circles available</option>');
                    }
                },
                error: function(error) {
                    console.log('Error:', error);
                    // Optionally, you can show an error message to the user
                    $('#circle').empty();
                    $('#circle').append('<option value="">Failed to load circles</option>');
                }
            });
        } else {
            // If no subdivision is selected, clear the circle dropdown
            $('#circle').empty();
            $('#circle').append('<option value="">Select</option>');
        }
    });

    $('#circle').change(function() {
            // Get the selected subdivision code
            var subdivCode = $('#subdiv').val();
            var circleCode = $(this).val();

            // If subdivision is selected, make an AJAX GET request to fetch circles based on subdivision
            if (subdivCode) {
                $.ajax({
                    url: '/secure/getMouzas', // Update this URL to match your API endpoint
                    type: "GET",
                    data: {
                                    subdivCode: subdivCode, // Send subdivCode as a query parameter
                                    circleCode: circleCode  // Send circleCode as a query parameter
                                },
                    success: function(data) {
                        console.log("Data for circles array", data);

                        // Clear the existing options in the #circle dropdown
                        $('#mouza').empty();

                        // Add a default "Select" option
                        $('#mouza').append('<option value="">Select</option>');

                        // Check if data has been returned, assuming it's an array of circles
                        if (data && data.length > 0) {
                            // Iterate over the data array and create options dynamically
                            data.forEach(function(circle) {
                                $('#mouza').append('<option value="' + circle.mouza_code + '">' + circle.loc_name + '</option>');
                            });
                        } else {
                            // In case no data is returned, you can show a message or leave empty
                            $('#mouza').append('<option value="">No circles available</option>');
                        }
                    },
                    error: function(error) {
                        console.log('Error:', error);
                        // Optionally, you can show an error message to the user
                        $('#mouza').empty();
                        $('#mouza').append('<option value="">Failed to load circles</option>');
                    }
                });
            } else {
                // If no subdivision is selected, clear the circle dropdown
                $('#mouza').empty();
                $('#mouza').append('<option value="">Select</option>');
            }
        });

    $('#mouza').change(function() {
                    // Get the selected subdivision code
                    var subdivCode = $('#subdiv').val();
                    var circleCode = $('#circle').val();
                    var mouzaCode = $(this).val();

                    // If subdivision is selected, make an AJAX GET request to fetch circles based on subdivision
                    if (circleCode) {
                        $.ajax({
                            url: '/secure/getLots', // Update this URL to match your API endpoint
                            type: "GET",
                            data: {
                                subdivCode: subdivCode,
                                circleCode: circleCode,
                                mouzaCode: mouzaCode
                            },
                            success: function(data) {
                                console.log("Data for circles array", data);

                                // Clear the existing options in the #circle dropdown
                                $('#lot').empty();

                                // Add a default "Select" option
                                $('#lot').append('<option value="">Select</option>');

                                // Check if data has been returned, assuming it's an array of circles
                                if (data && data.length > 0) {
                                    // Iterate over the data array and create options dynamically
                                    data.forEach(function(circle) {
                                        $('#lot').append('<option value="' + circle.lot_no + '">' + circle.loc_name + '</option>');
                                    });
                                } else {
                                    // In case no data is returned, you can show a message or leave empty
                                    $('#lot').append('<option value="">No circles available</option>');
                                }
                            },
                            error: function(error) {
                                console.log('Error:', error);
                                // Optionally, you can show an error message to the user
                                $('#lot').empty();
                                $('#lot').append('<option value="">Failed to load circles</option>');
                            }
                        });
                    } else {
                        // If no subdivision is selected, clear the circle dropdown
                        $('#lot').empty();
                        $('#lot').append('<option value="">Select</option>');
                    }
                });

    $('#lot').change(function() {
            // Get the selected subdivision code
            var subdivCode = $('#subdiv').val();
            var circleCode = $('#circle').val();
            var mouzaCode = $('#mouza').val();
            var lotNo = $(this).val();


            // If subdivision is selected, make an AJAX GET request to fetch circles based on subdivision
            if (circleCode) {
                $.ajax({
                    url: '/secure/getVillages', // Update this URL to match your API endpoint
                    type: "GET",
                    data: {
                        subdivCode: subdivCode,
                        circleCode: circleCode,
                        mouzaCode: mouzaCode,
                        lotNo: lotNo
                    },
                    success: function(data) {
                        console.log("Data for circles array", data);

                        // Clear the existing options in the #circle dropdown
                        $('#village').empty();

                        // Add a default "Select" option
                        $('#village').append('<option value="">Select</option>');

                        // Check if data has been returned, assuming it's an array of circles
                        if (data && data.length > 0) {
                            // Iterate over the data array and create options dynamically
                            data.forEach(function(circle) {
                                $('#village').append('<option value="' + circle.village_code + '">' + circle.loc_name + '</option>');
                            });
                        } else {
                            // In case no data is returned, you can show a message or leave empty
                            $('#village').append('<option value="">No circles available</option>');
                        }
                    },
                    error: function(error) {
                        console.log('Error:', error);
                        // Optionally, you can show an error message to the user
                        $('#village').empty();
                        $('#village').append('<option value="">Failed to load circles</option>');
                    }
                });
            } else {
                // If no subdivision is selected, clear the circle dropdown
                $('#village').empty();
                $('#village').append('<option value="">Select</option>');
            }
        });


        $('#village').change(function() {
                    // Get the selected subdivision code
                    var subdivCode = $('#subdiv').val();
                    var circleCode = $('#circle').val();
                    var mouzaCode = $('#mouza').val();
                    var lotNo = $('#lot').val();
                    var villageNo = $(this).val();


                    // If subdivision is selected, make an AJAX GET request to fetch circles based on subdivision
                    if (villageNo) {
                        $.ajax({
                            url: '/secure/getDags', // Update this URL to match your API endpoint
                            type: "GET",
                            data: {
                                subdivCode: subdivCode,
                                circleCode: circleCode,
                                mouzaCode: mouzaCode,
                                lotNo: lotNo,
                                villageNo: villageNo
                            },
                            success: function(data) {
                                console.log("Data for circles array", data);

                                // Clear the existing options in the #circle dropdown
                                $('#dag').empty();

                                // Add a default "Select" option
                                $('#dag').append('<option value="">Select</option>');

                                // Check if data has been returned, assuming it's an array of circles
                                if (data && data.length > 0) {
                                    // Iterate over the data array and create options dynamically
                                    data.forEach(function(dag) {
                                        $('#dag').append('<option value="' + dag.dag_no + '">' + dag.dag_no + '</option>');
                                    });
                                } else {
                                    // In case no data is returned, you can show a message or leave empty
                                    $('#dag').append('<option value="">No circles available</option>');
                                }
                            },
                            error: function(error) {
                                console.log('Error:', error);
                                // Optionally, you can show an error message to the user
                                $('#dag').empty();
                                $('#dag').append('<option value="">Failed to load dag number</option>');
                            }
                        });
                    } else {
                        // If no subdivision is selected, clear the circle dropdown
                        $('#dag').empty();
                        $('#dag').append('<option value="">Select</option>');
                    }
                });

//                $('#find-details').click(function () {
//                      // Get selected values
//                      var subdivCode = $('#subdiv').val();
//                      var circleCode = $('#circle').val();
//                      var mouzaCode = $('#mouza').val();
//                      var lotNo = $('#lot').val();
//                      var villageNo = $('#village').val();
//
//                      // Validate input before making the AJAX request
//                      if (!subdivCode || !circleCode || !mouzaCode || !lotNo || !villageNo) {
//                        alert("Please fill out all required fields.");
//                        return;
//                      }
//
//                      // Make AJAX GET request to fetch pattadar details
//                      $.ajax({
//                        url: '/getPattadarDetails', // Update this to match your API endpoint
//                        type: 'GET',
//                        data: {
//                          subdivCode: subdivCode,
//                          circleCode: circleCode,
//                          mouzaCode: mouzaCode,
//                          lotNo: lotNo,
//                          villageNo: villageNo
//                        },
//                        success: function (data) {
//                          console.log("Pattadar details fetched successfully:", data);
//
//                          // Clear existing table rows
//                          var tbody = $('#data-table tbody');
//                          tbody.empty();
//
//                          // Check if data is returned and populate the table
//                          if (data && data.length > 0) {
//                            data.forEach(function (item) {
//                              var row = `
//                                <tr>
//                                  <td>${item.Pattadar_name}</td>
//                                  <td>${item.Pattadar_father_name}</td>
//                                </tr>
//                              `;
//                              tbody.append(row);
//                            });
//
//                            // Show the table
//                            $('#data-table').show();
//                          } else {
//                            alert("No pattadar details found.");
//                            $('#data-table').hide();
//                          }
//                        },
//                        error: function (error) {
//                          console.log("Error fetching pattadar details:", error);
////                          alert("Failed to fetch pattadar details. Please try again later.");
//                          $('#data-table').hide();
//                        }
//                      });
//                    });


        $('#find-details').click(function () {
              // Get selected values
              var subdivCode = $('#subdiv').val();
              var circleCode = $('#circle').val();
              var mouzaCode = $('#mouza').val();
              var lotNo = $('#lot').val();
              var villageNo = $('#village').val();
              var dagNo = $('#dag').val();

              // Validate input before making the AJAX request
              if (!subdivCode || !circleCode || !mouzaCode || !lotNo || !villageNo || !dagNo) {
                 $.confirm({
                                    title: 'Missing Information',
                                    content: 'Please fill out all required fields.',
                                    type: 'red',
                                    typeAnimated: true,
                                    buttons: {
                                        ok: {
                                            text: "OK",
                                            btnClass: 'btn-red',
                                            action: function() {
                                                console.log("OK clicked");
                                            }
                                        }
                                    }
                                });
                return;
              }

              // Make AJAX GET request to fetch pattadar details
              $.ajax({
                url: '/secure/getPattadar', // Update this to match your API endpoint
                type: 'GET',
                data: {
                  subdivCode: subdivCode,
                  circleCode: circleCode,
                  mouzaCode: mouzaCode,
                  lotNo: lotNo,
                  villageNo: villageNo,
                  dagNo: dagNo
                },
                success: function (data) {
                  console.log("Pattadar details fetched successfully:", data);

                  // Clear existing table rows
                  var tbody = $('#data-table tbody');
                  tbody.empty();

                  // Check if data is returned and populate the table
                  if (data && data.length > 0) {
                    data.forEach(function (item) {
                      var row = `
                        <tr>
                          <td>${item.Pattadar_name}</td>
                          <td>${item.Pattadar_father_name}</td>
                        </tr>
                      `;
                      tbody.append(row);
                    });

                    // Show the table
                    $('#data-table').show();
                  } else {
                    alert("No pattadar details found.");
                    $('#data-table').hide();
                  }
                },
                error: function (error) {
                  console.log("Error fetching pattadar details:", error);
                  $('#data-table').hide();
                }
              });
            });


});
