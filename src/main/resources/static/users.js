// Заполнение страницы All users

$(async function () {
    await getAllUsers();
    showCurrentUser();
});

const allUsersTable = $('#tableAllUsers');

async function getAllUsers() {
    fetch("http://localhost:8080/api/")
        .then(res => res.json())
        .then(res => {
            allUsersTable.empty()
            res.forEach(user => {
                let tableWithUsers = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => " " + role.role)}</td>
                            <td>
                                <button type="button" class="btn btn-info" data-bs-toggle="modal" id="buttonEdit"
                                data-action="edit" data-bs-id="${user.id}" data-bs-target="#modalEdit">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" data-bs-toggle="modal" id="buttonDelete"
                                 data-action="delete" data-bs-id="${user.id}" data-bs-target="#modalDelete">Delete</button>
                            </td>
                        </tr>)`;

                allUsersTable.append(tableWithUsers);
            })
        })
}

const currentUserTable = $('#currentUserTable');

async function showCurrentUser() {
    currentUserTable.empty()
    fetch("http://localhost:8080/api/user")
        .then(res => res.json())
        .then(user => {
            let data = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => " " + role.role)}</td>
                        </tr>)`;
            $('#currentUserTable').append(data)
        })
}

// Создание нового пользователя
const formForNewUser = document.getElementById("formNewUser")
formForNewUser.addEventListener('submit', getFormValue);

async function getFormValue(event) {
    event.preventDefault();

    //вытаскиваем роли из формы
    const userFormAuthorities = formForNewUser.querySelector('#roles')
    const currentRoles = userFormAuthorities.selectedOptions
    let newUserRoles = [];

    for (let i = 0; i < currentRoles.length; i++) {
        console.log(i)
        newUserRoles.push({
            id: currentRoles.item(i).value,
            name: currentRoles.item(i).text
        })
    }

    // Отправляем запрос
    fetch("http://localhost:8080/api/", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify({
            firstName: formForNewUser.firstName.value,
            lastName: formForNewUser.lastName.value,
            age: formForNewUser.age.value,
            email: formForNewUser.email.value,
            userName: formForNewUser.userName.value,
            password: formForNewUser.password.value,
            roles: newUserRoles
        })
    })
        .then(() => {
            formForNewUser.reset();
            getAllUsers();
            $('#allUsers-tab').click();
        })

}

// модули

// Получение пользователя по ID
async function getUser(id) {
    let url = "http://localhost:8080/api/" + id;
    let response = await fetch(url);
    return await response.json();
}
function extracted(id, form, selectRole) {
    let user = getUser(id)
        .then(user => {
            form.id.value = user.id
            form.firstName.value = user.firstName
            form.lastName.value = user.lastName
            form.age.value = user.age
            form.email.value = user.email
            form.password.value = user.password
            form.userName.value = user.userName

            for (let option of selectRole.options) {
                for (let i = 0; i < user.roles.length; i++) {
                    if (parseInt(option.value) === user.roles[i].id) {
                        option.selected = true
                    }
                }
            }
        })
}

// Изменение пользователя
$('#modalEdit').on('show.bs.modal', function (event) {

    let userEditID = event.relatedTarget.getAttribute('data-bs-id')
    let form = document.querySelector('#formEdit')
    form.reset()
    let selectRole = document.getElementById('editRoles')
        // Заполнение формы
    extracted(userEditID, form, selectRole)

        // Отправка формы
    form.addEventListener('submit', function (ev) {
        ev.preventDefault()
        // Считывание ролей
        const userFormEditRole = document.querySelector('#editRoles')
        const currentRoles = userFormEditRole.selectedOptions
        let editRoles = [];

        for (let i = 0; i < currentRoles.length; i++) {
            editRoles.push({
                id: currentRoles.item(i).value,
                name: currentRoles.item(i).text
            })
        }
        // Отправка запросса на изменение
        fetch("http://localhost:8080/api/", {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: form.id.value,
                firstName: form.firstName.value,
                lastName: form.lastName.value,
                age: form.age.value,
                email: form.email.value,
                userName: form.userName.value,
                password: form.password.value,
                roles: editRoles

            })

        }).then(() => {
            $("#modalEdit").modal("hide")
            getAllUsers()
        })
    })
})


// Удаление пользователя
$('#modalDelete').on('show.bs.modal', function (event) {

    let userDeleteID = event.relatedTarget.getAttribute('data-bs-id')
    let form = document.querySelector('#deleteForm')
    form.reset()
    let selectRole = document.getElementById('deleteRoles')
    // Заполнение формы
    extracted(userDeleteID, form, selectRole);

    // Отправка формы
    form.addEventListener('submit', function (ev) {
        ev.preventDefault()
        // Считывание ролей
        const userFormEditRole = document.querySelector('#deleteRoles')
        const currentRoles = userFormEditRole.selectedOptions
        let deleteRoles = [];

        for (let i = 0; i < currentRoles.length; i++) {
            deleteRoles.push({
                id: currentRoles.item(i).value,
                name: currentRoles.item(i).text
            })
        }
        // Отправка запросса на изменение
        fetch("http://localhost:8080/api/"+userDeleteID, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: form.id.value
            })

        }).then(() => {
            $("#modalDelete").modal("hide")
            getAllUsers()
        })
    })
})


