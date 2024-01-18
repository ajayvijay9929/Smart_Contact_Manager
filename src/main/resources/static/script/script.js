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
        type: "GET",
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