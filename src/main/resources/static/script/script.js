console.log("this is console")

const toggleSidebar = () => {
    if ($(".sidebar").is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
        console.log("if cindeuctions")
    }
    else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");

    }
}


function deleteContact(cid, currentPage) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = "/user/" + cid + "/delete/" + currentPage;
        }
    });
}
// <!-- this is use for TinyMCE editor -->
//     <script src="https://cdn.tiny.cloud/1/no-api-key/tinymce/5/tinymce.min.js" referrerpolicy="origin"></script>
//     <script>
//       tinymce.init({
//         selector: '#mytextarea'
//       });
//     </script>

// this js for password updating 
function validateSignupForm() {
    var newPassword = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    if (newPassword !== confirmPassword) {
        alert("New Password and Confirm Password do not match!");
        return false; // Prevent form submission
    }

    if (newPassword.length < 5) {
        alert("Password must be at least 5 characters long!");
        return false; // Prevent form submission
    }

    // If the passwords match, you can proceed with form submission or other actions
    return true;
}

// for logout conformaction 
function confirmLogout() {
    window.location.href = '/logout'; // Redirect to the actual logout endpoint
}

function cancelLogout() {
    window.location.href = '/user/index'; // Redirect to some other page or dashboard
}

// for otp
function generateOtp(event) {
    // Prevent the default form submission
    event.preventDefault();
    var email = document.getElementById("email_field").value;
    var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;


    if (!email) {
        alert("Please enter email before generating OTP.");
        return;
    }

    if (!emailRegex.test(email)) {
        alert("Please enter valid email");
        return;
    }
    // Make an AJAX request to the server-side /opt endpoint
    $.ajax({
        type: "Get",
        url: "/otp",
        data: { email: email }, // Pass the email as a parameter
        success: function (otpGenerated) {
            if (otpGenerated) {
                alert("OTP Successfully Generated");
            } else {
                alert("Something went wrong check email address and try again!!");
            }
        },
        error: function (xhr, status, error) {
            console.error("AJAX request failed:", status, error);
            alert("Error generating OTP");
        }
    });
}

function validateOTP() {
    var userEnteredOtp = document.getElementById("otp").value; // Get the OTP entered by the user

    // Make an AJAX request to retrieve the OTP from the session
    $.ajax({
        type: "GET",
        url: "/getOtp",
        success: function (otpFromSession) {
            // Compare the OTP from the session with the user-entered OTP
            if (otpFromSession == userEnteredOtp) {
                // OTPs match, allow form submission
                document.getElementById("checkotp").submit();
            } else {
                // OTPs do not match, display error message
                alert("Please enter correct otp")
                return false; // Prevent form submission
            }
        },
        error: function () {
            // Error handling
            alert("try again!!")
            return false; // Prevent form submission
        }
    });

    // Prevent default form submission
    return false;
}


const search = () => {
    let query = $("#search-input").val();

    if (query == '') {
        $(".search-result").hide();
    }
    else {
        // sending request to server
        let url = `http://localhost:8080/search/${encodeURIComponent(query)}`;
        fetch(url).then((response) => {
            return response.json();
        }).then((data) => {
            console.log(data);
            let text = `<div class='list-group'>`;

            data.forEach(contact => {
                text += `<a href='/user/${contact.cId}/contact' class='list-group-item list-group-action'> ${contact.name} </a>`;
            });

            text += `</div>`;
            $(".search-result").html(text);
            $(".search-result").show();
        });
    }
};


// request create for the user patment (order)

const paymentStart = () => {
    console.log("payment started...");
    var amount = $("#payment_field").val();
    console.log(amount);
    if (amount == "" || amount == null) {
        alert("amount is required !!");
        return;
    }
    
    $.ajax({
        type: "POST",
        url: "/user/create_order",
        data: JSON.stringify({ amount: amount, info: 'order_request' }),
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            console.leg(response)
        },
        error: function (error) {
            console.log(error)
            alert("something went wrong !!")
        }
    })
};


