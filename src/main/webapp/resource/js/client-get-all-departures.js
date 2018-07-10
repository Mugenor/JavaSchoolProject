$(function showAllDepartures() {
    function timeRender(data, type, row, meta) {
        switch(type) {
            case "filter":
            case "display": return new Date(data).toLocaleString(undefined, {
                year: 'numeric', month: 'numeric', day: 'numeric',
                hour: 'numeric', minute: 'numeric'
            });
            case "sort": return data;
            case "type": return typeof data;
        }
    }

    function chooseDepartureButton(data, type, row, meta) {
        let departureDate = new Date(row.dateTimeFrom);
        debugger;
        departureDate.setMinutes(departureDate.getMinutes() - 10);
        if(departureDate.getTime() < new Date().getTime()) {
            return '<p>Too late!</p>';
        }
        if(row.freeSitsCount <= 0) {
            return '<p>There is no seats!</p>';
        }

        return $('<a/>', {
            class: 'btn btn-primary',
            href: '/client/select/' + row.id,
            target: '_blank'
        }).html('Buy ticket!').get(0).outerHTML;
    }

    $('#departures').DataTable({
        responsive: true,
        paging: false,
        ajax: {
            url: '/departure',
            dataSrc: ''
        },
        columns: [
            { data: 'stationFrom'},
            { data: 'stationTo'},
            { data: 'dateTimeFrom', render: timeRender},
            { data: 'dateTimeTo', render: timeRender},
            { data: 'freeSitsCount'},
        ],
        columnDefs: [{
            targets: 5,
            searchable: false,
            orderable: false,
            render: chooseDepartureButton
        }]
    });
});