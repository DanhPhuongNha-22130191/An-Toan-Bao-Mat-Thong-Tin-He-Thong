
$(document).ready(function() {
    // Prevent form submission on clicking the radio buttons (if any)
    $("#brandFilterForm").submit(function(event) {
        event.preventDefault();  // Prevent page reload
    });
});

// Function to filter products by brand via AJAX
function filterProductsByBrand(brandId) {
    $.ajax({
        url: '/ATBM/CategoryController',  // Same URL as the CategoryController
        type: 'GET',
        data: { brandId: brandId },  // Send the selected brandId to the server
        success: function(response) {
            // Update the product list section with the new products
            $('#productList').html($(response).find('#productList').html());  // Only update the #productList content
        },
        error: function(xhr, status, error) {
            // Detailed error message
            console.log('AJAX request failed');
            console.log('Status: ' + status);
            console.log('Error: ' + error);
            console.log('Response Text: ' + xhr.responseText);
            alert('An error occurred while filtering products.');
        }
    });
}
