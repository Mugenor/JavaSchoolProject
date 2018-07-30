$(function showAllDepartures() {
    let stations = [];
    let stationFromInput = $('#stationFrom');
    let stationToInput = $('#stationTo');
    let dateTimeFromInput = $('#dateTimeFrom');
    let dateTimeToInput = $('#dateTimeTo');
    $.ajax('station', {
        method: "GET",
        success: function (resp) {
            for (let i = 0; i < resp.length; ++i) {
                stations.push(resp[i].name);
            }
            $('.autocomplete').autocomplete({
                source: stations,
                change: function (event, ui) {
                    if (ui.item) {
                        $(this).val(ui.item.label);
                        $(this).removeClass('is-invalid');
                        $(this).addClass('is-valid');
                        return;
                    }
                    for (let i = 0; i < stations.length; ++i) {
                        if (stations[i].startsWith($(this).val())) {
                            $(this).removeClass('is-invalid');
                            $(this).addClass('is-valid');
                            $(this).val(stations[i]);
                            return;
                        }
                    }
                    $(this).removeClass('is-valid');
                    $(this).addClass('is-invalid');
                    $(this).val('');
                }
            });
        }
    });

    function timeRender(data, type, row, meta) {
        switch (type) {
            case "filter":
            case "display":
                return new Date(data).toLocaleString(undefined, {
                    year: 'numeric', month: 'numeric', day: 'numeric',
                    hour: 'numeric', minute: 'numeric'
                });
            case "sort":
                return data;
            case "type":
                return typeof data;
        }
    }

    function chooseDepartureButton(data, type, row, meta) {
        let departureDate = new Date(row.dateTimeFrom);
        departureDate.setMinutes(departureDate.getMinutes() - 10);
        if (departureDate.getTime() < new Date().getTime()) {
            return '<p>Too late!</p>';
        }
        if (row.freeSitsCount <= 0) {
            return '<p>There is no seats!</p>';
        }

        return $('<a/>', {
            class: 'btn btn-primary',
            href: 'client/select/' + row.id,
            target: '_blank'
        }).html('Buy ticket!').get(0).outerHTML;
    }

    let today = new Date();
    let lastDate = new Date();
    lastDate.setFullYear(lastDate.getFullYear() + 5);

    $("#dateTimeFrom, #dateTimeTo").datetimepicker({
        format: "dd.mm.yyyy, HH:ii",
        startDate: today,
        endDate: lastDate,
        immediateUpdates: true
    }).on('change.dp', function (event) {
        let departureTime = dateTimeFromInput.data('datetimepicker').getDate();
        let arrivalTime = dateTimeToInput.data('datetimepicker').getDate();
        let now = new Date();
        now.setMinutes(now.getMinutes() + 10);
        if(departureTime.getTime() <= now.getTime() || departureTime.getTime() >= arrivalTime.getTime()) {
            dateTimeFromInput.removeClass('is-valid');
            dateTimeFromInput.addClass('is-invalid');
        } else {
            dateTimeFromInput.removeClass('is-invalid');
            dateTimeFromInput.addClass('is-valid');
        }
        if(arrivalTime.getTime() <= departureTime.getTime() || arrivalTime.getTime() <= now.getTime()) {
            dateTimeToInput.removeClass('is-valid');
            dateTimeToInput.addClass('is-invalid');
        } else {
            dateTimeToInput.removeClass('is-invalid');
            dateTimeToInput.addClass('is-valid')
        }
    });


    $('#stationFrom, #stationTo').change(function (event) {
        if (stationFromInput.val() === stationToInput.val()) {
            stationFromInput.removeClass('is-valid');
            stationToInput.removeClass('is-valid');
            stationFromInput.addClass('is-invalid');
            stationToInput.addClass('is-invalid');
        } else {
            stationFromInput.removeClass('is-invalid');
            stationToInput.removeClass('is-invalid');
            stationFromInput.addClass('is-valid');
            stationToInput.addClass('is-valid');
        }
        if(stationFromInput.val() === '') {
            stationFromInput.removeClass('is-valid');
            stationFromInput.addClass('is-invalid');
        }
        if(stationToInput.val() === '') {
            stationToInput.removeClass('is-valid');
            stationToInput.addClass('is-invalid');
        }
    });

    let departureTable = $('#departures');

    $('#departure-form').submit(function (event) {
        if (stationToInput.hasClass('is-valid') && stationFromInput.hasClass('is-valid')
            && dateTimeFromInput.hasClass('is-valid') && dateTimeToInput.hasClass('is-valid')) {
            $.ajax('departure/find', {
                data: {stationFrom: stationFromInput.val(), stationTo: stationToInput.val(),
                    dateTimeFrom: dateTimeFromInput.data('datetimepicker').getDate().getTime(),
                    dateTimeTo: dateTimeToInput.data('datetimepicker').getDate().getTime()},
                success: function (resp) {
                    departureTable.DataTable().rows().remove().draw();
                    departureTable.DataTable().rows.add(resp).draw();
                }
            });
        }
        return false;
    });


    departureTable.DataTable({
        responsive: true,
        paging: false,
        columns: [
            {data: 'stationFrom'},
            {data: 'stationTo'},
            {data: 'dateTimeFrom', render: timeRender},
            {data: 'dateTimeTo', render: timeRender},
            {data: 'freeSitsCount'},
        ],
        bAutoWidth: false,
        columnDefs: [{
            targets: 5,
            searchable: false,
            orderable: false,
            render: chooseDepartureButton
        }]
    });
});