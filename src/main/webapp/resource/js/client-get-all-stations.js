function showAllStations() {
    $('#main-block').children().remove();
    let table = $('<table/>', {
        id: 'stations',
        class: 'table table-striped table-bordered table-light'
    });
    let thead = $('<thead/>')
        .append($('<tr/>')
            .append($('<th/>').html('Station'))).appendTo(table);
    table.appendTo($('#main-block'));
    $('#stations').DataTable({
        select: true,
        paging: false,
        ajax: {
            url: '/station',
            dataSrc: ''
        },
        columns: [
            { data: 'name'},
        ]
    });
}
$('#all-stations').click(showAllStations);
