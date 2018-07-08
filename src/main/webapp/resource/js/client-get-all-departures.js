function showAllDepartures() {
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
            href: '/buy/' + row.id,
            target: '_blank'
        }).html('Buy ticket!').get(0).outerHTML;
    }

    $('#main-block').children().remove();
    let div = $('<div/>', {
        class: 'container-fluid'
    });
    let table = $('<table/>', {
        id: 'departures',
        class: 'table table-striped table-bordered table-light'
    }).appendTo(div);
    let thead = $('<thead/>')
        .append($('<tr/>')
            .append($('<th/>').html('Station of departure'))
            .append($('<th/>').html('Arrival station'))
            .append($('<th/>').html('Time of departure'))
            .append($('<th/>').html('Arrival time'))
            .append($('<th/>').html('Number of available seats'))
            .append($('<th/>').html('Get ticket'))).appendTo(table);
    div.appendTo($('#main-block'));
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
}
$('#all-departures').click(showAllDepartures);
