$(function showAllDepartures() {
    $('#departures').DataTable({
        responsive: true,
        paging: false,
        order: [[2, 'asc']],
        columnDefs: [{
            targets: 5,
            searchable: false,
            orderable: false,
        }],
        bAutoWidth: false
    });
});