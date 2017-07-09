$(document).ready(function () {

    var amount = 0;

    $('#dollar-button').on('click', function () {
        amount += 1;
        $('#show-money').val(amount.toFixed(2));
    });

    $('#quarter-button').on('click', function () {
        amount += 0.25;
        $('#show-money').val(amount.toFixed(2));
    });

    $('#dime-button').on('click', function () {
        amount += 0.10;
        $('#show-money').val(amount.toFixed(2));
    });

    $('#nickel-button').on('click', function () {
        amount += 0.05;
        $('#show-money').val(amount.toFixed(2));

    });

    $('#purchase-button').on('click', function () {
        var itemId = $('#item-select').val();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/money/' + amount + '/item/' + itemId,
            success: function (data, status) {

                var qString = ' quarters ';
                if (data.quarters === 1) {
                    qString = ' quarter ';
                }
                var dString = ' dimes ';
                if (data.dimes === 1) {
                    dString = ' dime ';
                }
                var nString = ' nickels ';
                if (data.nickels === 1) {
                    nString = ' nickel ';
                }
                var pString = ' pennies';
                if (data.pennies === 1) {
                    pString = ' penny';
                }

                if (data.quarters === 0) {
                    qString = '';
                    data.quarters = '';
                }
                if (data.dimes === 0) {
                    dString = '';
                    data.dimes = '';
                }
                if (data.nickels === 0) {
                    nString = '';
                    data.nickels = '';
                }
                if (data.pennies === 0) {
                    pString = '';
                    data.pennies = '';
                }

                document.getElementById('show-change').value = data.quarters + qString + data.dimes + dString + data.nickels + nString + data.pennies + pString;

                amount = 0;
                document.getElementById('show-money').value = amount;

                document.getElementById('show-message').value = 'Thank You!!!';
            },
            error: function (data) {
                document.getElementById('show-message').value = data.responseJSON.message;
                if ($('#item-select').val() === '') {
                    document.getElementById('show-message').value = 'Please select an item.';
                }
            }
        });
    });

    $('#change-button').on('click', function () {
        var quarters = 0;
        var dimes = 0;
        var nickels = 0;
        var pennies = 0;
        while (amount > 0.25 || amount === 0.25) {
            quarters++;
            amount -= 0.25; //.toFixed(2)
        }
        while (amount > 0.10 || amount === 0.10) {
            dimes++;
            amount -= 0.10; //.toFixed(2)
        }
        while (amount > 0.05 || amount === 0.05) {
            nickels++;
            amount -= 0.05; //.toFixed(2)
        }
        while (amount > 0.01 || amount === 0.01) {
            pennies++;
            amount -= 0.01; //.toFixed(2)
        }
        var qString = ' quarters ';
        if (quarters === 1) {
            qString = ' quarter ';
        }
        var dString = ' dimes ';
        if (dimes === 1) {
            dString = ' dime ';
        }
        var nString = ' nickels ';
        if (nickels === 1) {
            nString = ' nickel ';
        }
        var pString = ' pennies';
        if (pennies === 1) {
            pString = ' penny';
        }

        if (quarters === 0) {
            qString = '';
            quarters = '';
        }
        if (dimes === 0) {
            dString = '';
            dimes = '';
        }
        if (nickels === 0) {
            nString = '';
            nickels = '';
        }
        if (pennies === 0) {
            pString = '';
            pennies = '';
        }

        document.getElementById('show-change').value = quarters + qString + dimes + dString + nickels + nString + pennies + pString;


        document.getElementById('show-money').value = 0;

        $('#item-select').val('');
        $('#show-message').val('');
        loadItems();
    });

    loadItems();
});

function clearItemTable() {
    $('#contentRows').empty();
}

function loadItems() {
    // we need to clear the previous content so we don't append to it
    clearItemTable();

    // grab the the tbody element that will hold the rows of contact information
    var contentRows = $('#contentRows');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function (data, status) {
            $.each(data, function (index, item) {
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var id = item.id;

                var div = '<div class="col-sm-4">';

                div += '<div class="btn btn-default btn-block itemBlock" onclick="selectItem(' + id + ')" id="item-button"><div align="left">' + id +
                        '</div>' + name + '<br><br>$' + price.toFixed(2) + '<br><br><br>' + 'Quantity Left: ' + quantity + '</div>';
                div += '</div>';
                contentRows.append(div);
            });
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function selectItem(id) {
    $('#item-select').val(id);
    document.getElementById('item-select').value = id;
}


// processes validation errors for the given input.  returns true if there
// are validation errors, false otherwise
function checkAndDisplayValidationErrors(input) {
    // clear displayed error message if there are any
    $('#errorMessages').empty();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    // loop through each input and check for validation errors
    input.each(function () {
        // Use the HTML5 validation API to find the validation errors
        if (!this.validity.valid)
        {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({class: 'list-group-item list-group-item-danger'}).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}

