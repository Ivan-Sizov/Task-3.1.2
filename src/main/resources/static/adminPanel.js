window.onload = populateAllUsersTable;

function populateEditForm(user) {
    const roles = user.roles.map(function (role) {
        return role.role.replace('ROLE_', '');
    });
    $('#edit-id').val(user.id);
    $('#edit-name').val(user.name);
    $('#edit-surname').val(user.surname);
    $('#edit-age').val(user.age);
    $('#edit-email').val(user.email);
    $('#edit-username').val(user.username);

    roles.forEach(function (role) {
        $('#edit-roles option[value="' + role.replace('ROLE_', '') + '"]').prop('selected', true);
    });
}

function editUser(e) {
    e.preventDefault();

    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    const id = $('#edit-id').val();

    const userJson = {
        id: id,
        name: $('#edit-name').val(),
        surname: $('#edit-surname').val(),
        age: $('#edit-age').val(),
        email: $('#edit-email').val(),
        username: $('#edit-username').val(),
        password: $('#edit-password').val(),
        roles: $('#edit-roles').val()
    };

    $.ajax({
        url: '/api/users/' + userJson.id,
        type: 'PUT',
        data: JSON.stringify(userJson),
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            $('#editUserModal').modal('hide');
            deleteTableContents();
            populateAllUsersTable();
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}

function deleteUser(e) {
    e.preventDefault();
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    const id = $('#delete-id').val();

    $.ajax({
        url: '/api/users/' + id,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            $('#deleteUserModal').modal('hide');
            deleteRow(id);
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}

function populateDeleteForm(user) {
    const roles = user.roles.map(function (role) {
        return role.role.replace('ROLE_', '');
    });
    $('#delete-id').val(user.id);
    $('#delete-name').val(user.name);
    $('#delete-surname').val(user.surname);
    $('#delete-age').val(user.age);
    $('#delete-email').val(user.email);
    $('#delete-username').val(user.username);

    roles.forEach(function (role) {
        $('#delete-roles option[value="' + role.replace('ROLE_', '') + '"]').prop('selected', true);
    });
}

function populateAllUsersTable() {
    fetch('/api/users')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("tbody");

            data.forEach(user => {
                const tr = document.createElement("tr");
                tr.id = "user-row-" + user.id;

                const keys = ['id', 'name', 'surname', 'age', 'email'];
                keys.forEach(key => {
                    const td = document.createElement("td");
                    td.textContent = user[key];
                    td.id = key + "-" + user.id
                    tr.appendChild(td);
                });

                const roleTd = document.createElement("td");
                roleTd.id = user.id;
                user.roles.forEach(userRole => {
                    const span = document.createElement("span");
                    span.textContent = userRole.role.substring(5) + " ";
                    roleTd.appendChild(span);
                });
                tr.appendChild(roleTd);

                const editButtonTd = document.createElement("td");
                const editButton = document.createElement("button");
                editButton.textContent = "Edit";
                editButton.className = "btn btn-info";
                editButton.setAttribute("data-toggle", "modal");
                editButton.setAttribute("data-target", "#editUserModal");
                editButton.onclick = function () {
                    populateEditForm(user);
                };
                editButtonTd.appendChild(editButton);
                tr.appendChild(editButtonTd);

                const deleteButtonTd = document.createElement("td");
                const deleteButton = document.createElement("button");
                deleteButton.textContent = "Delete";
                deleteButton.className = "btn btn-danger";
                deleteButton.setAttribute("data-toggle", "modal");
                deleteButton.setAttribute("data-target", "#deleteUserModal");
                deleteButton.onclick = function () {
                    populateDeleteForm(user);
                };
                deleteButtonTd.appendChild(deleteButton);
                tr.appendChild(deleteButtonTd);

                tbody.appendChild(tr);
            });
        });
}

function deleteRow(id) {
    const tr = document.querySelector("#user-row-" + id);
    if (tr) {
        tr.remove();
    }
}

function deleteTableContents() {
    let tbody = document.querySelector("tbody");
    if (tbody) {
        tbody.remove();
    }
    tbody = document.createElement("tbody");
    const table = document.querySelector("table");
    table.appendChild(tbody);
}