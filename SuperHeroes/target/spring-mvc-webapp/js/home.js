$(document).ready(function () {

    $('#add-organization-list').on('click', function () {
        $('#choose-organization-list').clone().appendTo($('#org-list-append'));
    });

    $('#add-power-list').on('click', function () {
        $('#choose-power-list').clone().appendTo($('#pow-list-append'));
    });

    $('#add-MH-list').on('click', function () {
        $('#choose-MH-list').clone().appendTo($('#MH-list-append'));
    });

    $('#delete-confirm').on('click', function () {
        alert("Are you sure you want to delete?");
    });

});