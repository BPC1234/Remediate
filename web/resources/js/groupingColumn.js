$(document).ready(function () {

    var data = new Array();

    var firstNames =
        [
            "Andrew", "Nancy", "Shelley", "Regina", "Yoshi", "Antoni", "Mayumi", "Ian", "Peter", "Lars", "Petra", "Martin", "Sven", "Elio", "Beate", "Cheryl", "Michael", "Guylene"
        ];
    var lastNames =
        [
            "Fuller", "Davolio", "Burke", "Murphy", "Nagase", "Saavedra", "Ohno", "Devling", "Wilson", "Peterson", "Winkler", "Bein", "Petersen", "Rossi", "Vileid", "Saylor", "Bjorn", "Nodier"
        ];
    var productNames =
        [
            "Black Tea", "Green Tea", "Caffe Espresso", "Doubleshot Espresso", "Caffe Latte", "White Chocolate Mocha", "Cramel Latte", "Caffe Americano", "Cappuccino", "Espresso Truffle", "Espresso con Panna", "Peppermint Mocha Twist"
        ];
    var cityNames =
        [
            "Dhaka", "London", "Comilla", "Dhaka", "Chandpur", "Chandpur", "Dhaka", "Shylet", "Noakhali", "Munshiganj", "Dhaka", "NGonj"
        ];
    var priceValues =
        [
            "2.25", "1.5", "3.0", "3.3", "4.5", "3.6", "3.8", "2.5", "5.0", "1.75", "3.25", "4.0"
        ];


    var firstColumn = "First Column";

    for (var i = 0; i < 50; i++) {
        var row = {};
        var productindex = Math.floor(Math.random() * productNames.length);
        var price = parseFloat(priceValues[productindex]);
        var quantity = 1 + Math.round(Math.random() * 10);

        row["firstname"] = firstNames[i];
        row["lastname"] = lastNames[i];
        row["cityname"] = cityNames[i];
        row["productname"] = productNames[productindex];


        data[i] = row;

    }
    var source =
    {
        localdata: data,
        datatype: "array"
    };
    $("#groupTable").jqxGrid(
        {
            height: 300,
            source: source,
            groupable: true,
            columns: [
                { text: firstColumn, datafield: 'firstname' },
                { text: 'Last Name', datafield: 'lastname' },
//                        { text: 'Product', datafield: 'productname' },
                { text: 'city Name', datafield: 'cityname' }
            ],
            groups: ['firstname']
        });

});