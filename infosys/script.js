const BASE_URL = "http://localhost:8080";
let isLoggedIn = false;

/* ---------------- AUTH UI CONTROL ---------------- */
function showAuth(id) {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("registerForm").style.display = "none";
    document.getElementById(id).style.display = "block";
}

function login() {
    const data = {
        email: document.getElementById("l-email").value,
        password: document.getElementById("l-pass").value
    };

    fetch(BASE_URL + "/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(user => {
            const msgEl = document.getElementById("login-result");

            if (user != null) {
                isLoggedIn = true;
                document.getElementById("authButtons").style.display = "none";
                document.getElementById("logoutBox").style.display = "block";
                document.getElementById("loginForm").style.display = "none";
                document.getElementById("registerForm").style.display = "none";
                document.getElementById("bankActions").style.display = "block";
                msgEl.className = "success";
                msgEl.innerText = "Login successful!";
                clearInputs(["l-email", "l-pass"]);
            } else {
                msgEl.className = "error";
                msgEl.innerText = "Invalid credentials";
            }
        });
}

function register() {
    const data = {
        name: document.getElementById("r-name").value,
        email: document.getElementById("r-email").value,
        password: document.getElementById("r-pass").value
    };

    fetch(BASE_URL + "/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            const msgEl = document.getElementById("register-result");
            msgEl.className = "success";
            msgEl.innerText = msg;
            clearInputs(["r-name", "r-email", "r-pass"]);
        });
}

function logout() {
    isLoggedIn = false;
    document.getElementById("bankActions").style.display = "none";
    document.getElementById("authButtons").style.display = "block";
    document.getElementById("logoutBox").style.display = "none";
}

/* ---------------- SECTION CONTROL ---------------- */
function showSection(id) {
    document.querySelectorAll(".section").forEach(sec => sec.style.display = 'none');
    document.getElementById(id).style.display = 'block';
}

/* ---------------- BANKING FEATURES ---------------- */

function createAccount() {
    const data = {
        name: document.getElementById("c-name").value,
        email: document.getElementById("c-email").value,
        balance: document.getElementById("c-balance").value
    };

    fetch(BASE_URL + "/accounts/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(result => {
            const msgEl = document.getElementById("create-result");
            msgEl.className = "success";
            msgEl.innerText = "Account created! Your Account Number: " + result.accountNumber;
            clearInputs(["c-name", "c-email", "c-balance"]);
        });
}

function deposite() {
    const data = {
        accNo: document.getElementById("d-acc").value,
        amount: document.getElementById("d-amount").value
    };

    fetch(BASE_URL + "/transactions/deposite", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            const msgEl = document.getElementById("deposite-result");
            msgEl.className = "success";
            msgEl.innerText = msg;
            clearInputs(["d-acc", "d-amount"]);
        });
}

function withdraw() {
    const data = {
        accNo: document.getElementById("w-acc").value,
        amount: document.getElementById("w-amount").value
    };

    fetch(BASE_URL + "/transactions/withdraw", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            const msgEl = document.getElementById("withdraw-result");
            msgEl.className = "success";
            msgEl.innerText = msg;
            clearInputs(["w-acc", "w-amount"]);
        });
}

function transfer() {
    const data = {
        fromAcc: document.getElementById("t-from").value,
        toAcc: document.getElementById("t-to").value,
        amount: document.getElementById("t-amount").value
    }

    fetch(BASE_URL + "/transactions/transfer", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            const msgEl = document.getElementById("trasfer-result");
            msgEl.className = "success";
            msgEl.innerText = msg;
            clearInputs(["t-from", "t-to", "t-amount"]);
        });
};

function viewAccount() {
    const acc = document.getElementById("v-acc").value;

    fetch(BASE_URL + "/accounts/" + acc)
        .then(res => {
            if (!res.ok) {
                throw new Error("Account not found");
            }
            return res.json();
        })
        .then(data => {
            const output = `
            <div class="account-row">
                <span>Account Number</span>
                <div>${data.accountNumber}</div>
            </div>
            <div class="account-row">
                <span>Name</span>
                <div>${data.holderName}</div>
            </div>
            <div class="account-row">
                <span>Email</span>
                <div>${data.email}</div>
            </div>
            <div class="account-row">
                <span>Balance</span>
                <div>₹${data.openingBalance}</div>
            </div>
        `;
            document.getElementById("view-result").innerHTML = output;
            clearInputs(["v-acc"]);
        })
        .catch(err => {
            const msgEl = document.getElementById("view-result");
            msgEl.className = "error";
            msgEl.innerText = err.message;
        });
}

function listAccount(){
    fetch(BASE_URL+"/accounts/all")
    .then(res => res.json())
    .then(data => {

        let output = `
            <table class="accounts-table">
                <tr>
                    <th>Account Number</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Balance</th>
                </tr>
        `;

        data.forEach(acc => {
            output += `
                <tr>
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td>${acc.email}</td>
                    <td>₹${acc.openingBalance}</td>
                </tr>
            `;
        });

        output += `</table>`;

        document.getElementById("list-result").innerHTML = output;
    });
}
 
function clearInputs(ids) {
    ids.forEach(id => {
        document.getElementById(id).value = "";
    });
}