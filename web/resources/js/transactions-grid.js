$(function () {
	var fields=['id', 'transactionType', 'amount', 'createdText', 'createdBy'];
	var customCol=[		{text: "ID",
				        width: 50,
				        sortable: true,
				        dataIndex: 'id',
				        },
				        {text: "Transaction Type",
			             width: 170,
			             sortable: true,
			             dataIndex: 'transactionType',
			             },
			            {text: "Amount",
			             width: 200,
			             sortable: true,
			             dataIndex: 'amount',
			             },
			            {text: "Created Date",			            	 
			            	 width: 200,
				             sortable: true,
				             dataIndex: 'createdText'
				         },
				        {text: "Created By",
				             width: 150,
				             sortable: true,
				             dataIndex: 'createdBy',
				         }
			        ];
	loadPagingGrid (  fields
					, './transactionsGrid.html'
					, 'transactions'
					, customCol
					, 400
					, 'transactions-grid'
					, 'Transactions from CSV file'
					, 'Displaying transactions {0} - {1} of {2}'
					, 'No transactions to display'
					,function(dv, record, item, index, e) {})
}); // end main function

