function addTask() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/add',
        data: JSON.stringify({
            description: $('#description').val()
        }),
        dataType: 'json'
    }).done(location.reload()
    ).fail(function (err) {
        console.log(err);
    });
}

function setDoneStatus(id) {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/todo/setdonestatus',
        data: JSON.stringify({
            id: id
        }),
        dataType: 'json'
    }).done()
        .fail(function (err) {
            console.log(err);
        });
    location.reload();
}

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/add',
        dataType: 'json'
    }).done(function (data) {
        for (var task of data) {
            if (task.done !== true) {
                $('#descriptionList table:last').append(
                    `<tr>
                        <td>${task.description}</td>
                        <td>${task.created}</td>
                        <td>${task.user.name}</td>
                        <td><input type="checkbox" onchange="setDoneStatus(${task.id})"></td>                     
                     </tr>`)
            }
        }
    }).fail(function (err) {
        console.log(err);
    });
});

function showAll() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/add',
        dataType: 'json'
    }).done(function (data) {
        if ($("#check").prop('checked')) {
            for (var task of data) {
                if (task.done === true) {
                    $('#descriptionList table:last').append(
                        `<tr>
                            <td>${task.description}</td>
                            <td>${task.created}</td>
                            <td>${task.user.name}</td>
                            <td><input type="checkbox" checked onchange="setDoneStatus(${task.id})"></td>                     
                        </tr>`)
                }
            }
        } else {
            location.reload();
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    let message = 'Enter description of task'
    let description = $('#description').val();
    if (description === '') {
        alert(message);
        return false;
    }
    addTask();
    return true;
}