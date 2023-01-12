
function login() {
    var username = document.getElementById("username").value;
    var code = document.getElementById("code").value;
    
    window.history.replaceState("", "", getUriWithParam(document.URL, {type: "login", username: username, code: code}))
}

const getUriWithParam = (baseUrl, params) => {
    const Url = new URL(baseUrl);
    const urlParams = new URLSearchParams(Url.search);
    for (const key in params) {
      if (params[key] !== undefined) {
        urlParams.set(key, params[key]);
      }
    }
    Url.search = urlParams.toString();
    return Url.toString();
  };