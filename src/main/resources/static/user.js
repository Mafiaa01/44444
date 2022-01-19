showUserInfo();

function showUserInfo() {
    fetch('http://localhost:8080/api/getUser')
        .then(response => response.json())
        .then(user => {
            let tBody = document.getElementById("user_info");
            tBody.innerHTML = "";

            const row = tBody.insertRow(0);
            const cell0 = row.insertCell(0);
            cell0.innerHTML = user.id;
            const cell1 = row.insertCell(1);
            cell1.innerHTML = user.email;
            const cell2 = row.insertCell(2);
            cell2.innerHTML = user.password;
            const cell3 = row.insertCell(3);
            cell3.innerHTML = user.username;
            const cell4 = row.insertCell(4);
            cell4.innerHTML = user.roleName;
        })
        .catch(err => console.error('Error: ', err));

}

showHeader()

function showHeader() {
    fetch('http://localhost:8080/api/getUser')
        .then(response => response.json())
        .then(user => {
            document.getElementById("header_username").innerHTML = user.username;
            document.getElementById("header_roles").innerHTML = 'with roles: ' + user.roleName;
        });
}