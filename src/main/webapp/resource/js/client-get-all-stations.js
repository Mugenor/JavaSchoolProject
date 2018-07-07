function showAllDepartures() {
    $('#main-block').children().remove();
    let table = $('<table/>', {
        id: 'departures',
        class: 'table table-striped table-bordered table-light'
    });
    let thead = $('<thead/>')
        .append($('<tr/>')
            .append($('<th/>').html('Station of departure'))
            .append($('<th/>').html('Arrival station'))
            .append($('<th/>').html('Time of departure'))
            .append($('<th/>').html('Arrival time'))
            .append($('<th/>').html('Number of available seats'))).appendTo(table);
    // let tbody = $('<tbody/>')
    //     .append($('<tr/>')
    //         .append($('<th/>').html('Bla'))
    //         .append($('<th/>').html('Bla'))).appendTo(table);
    table.appendTo($('#main-block'));
    $('#departures').DataTable({
        ajax: {
            url: '/departure',
            dataSrc: ''
        },
        columns: [
            { data: 'stationFrom'},
            { data: 'stationTo'},
            { data: 'dateTimeFrom'},
            { data: 'dateTimeTo'},
            { data: 'freeSitsCount'},
        ]
    });
}
$('#all-departures').click(showAllDepartures);
