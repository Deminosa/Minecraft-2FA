
function register() {
    var username = document.getElementById("username").value;
    var code = document.getElementById("code").value;
    
    window.history.replaceState("", "", getUriWithParam(document.URL, {type: "reg", username: username, code: code}))
    wait
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