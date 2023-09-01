function injectStyles() {
  const styles = `
  .alert-container {
    position: fixed;
    display: flex;
    justify-content: center;
    z-index: 1000;
    transform: translateX(-50%); /* Centering */
  }
  .alert {
    border-radius: 10px;
    padding: 1rem;
    text-align: center;
    font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
      Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
    font-weight: 500;
  }
  .alert-container.top {
    top: 10px;
    left: 50%;
  }
  .alert-container.bottom {
    bottom: 10px;
    left: 50%;
  }
  
  .alert-primary { background-color: #95d8ff; color: white; }
  .alert-success { background-color: #d8ffce; color: #478039; border: 1px solid #478039; }
  .alert-danger { background-color: #ffd3d3; color: #8e3737; border: 1px solid #8e3737; }
  .alert-warning { background-color: #FBFFE0; color: #78842f; border: 1px solid #78842f; }
  .alert-info { background-color: #f0feff; color: #70898a; border: 1px solid #70898a; }
  
  .fade-up-in {
    animation: fadeUpIn 1s forwards;
  }
  
  @keyframes fadeUpIn {
    0% { opacity: 0; transform: translateY(10px); }
    100% { opacity: 1; transform: translateY(0); }
  }
  
  .fade-up-out {
    animation: fadeDownOut 1s forwards;
  }
  
  @keyframes fadeDownOut {
    0% { opacity: 1; transform: translateY(0); }
    100% { opacity: 0; transform: translateY(10px); }
  }
  
  .fade-in-in {
    animation: fadeInIn 1s forwards;
  }
  
  @keyframes fadeInIn {
    0% { opacity: 0; }
    100% { opacity: 1; }
  }
  
  .fade-in-out {
    animation: fadeOutOut 1s forwards;
  }
  
  @keyframes fadeOutOut {
    0% { opacity: 1; }
    100% { opacity: 0; }
  }
  
    `;
  const styleSheet = document.createElement("style");
  styleSheet.type = "text/css";
  styleSheet.innerText = styles;
  document.head.appendChild(styleSheet);
}
injectStyles();

const alertQueue = [];
let currentAlert = null;
function processQueue() {
  null === currentAlert &&
    alertQueue.length > 0 &&
    (currentAlert = alertQueue.shift()).show();
}
class Alert {
  constructor(t, i, e, n, s, a = "bottom") {
    (this.text = t),
      (this.duration = i),
      (this.state = e),
      (this.animation = n),
      (this.animationDuration = s || "0.5s"),
      (this.position = a);
  }
  show() {
    if (
      !["primary", "success", "danger", "warning", "info"].includes(this.state)
    ) {
      console.error(
        `Invalid state: ${this.state}. State must be one of ["primary", "success", "danger", "warning", "info"].`
      );
      return;
    }
    if ("number" != typeof this.duration || this.duration <= 0) {
      console.error(
        `Invalid duration: ${this.duration}. Duration must be a positive number.`
      );
      return;
    }
    if (
      "string" != typeof this.animationDuration ||
      !/^(\d+(\.\d+)?s)$/.test(this.animationDuration)
    ) {
      console.error(
        `Invalid animationDuration: ${this.animationDuration}. Animation duration must be a string representing seconds (e.g., "0.5s").`
      );
      return;
    }
    if (!["bottom", "top"].includes(this.position)) {
      console.error(
        `Invalid position: ${this.position}. Position must be one of ["bottom", "top"].`
      );
      return;
    }
    let t = document.createElement("div");
    t.className = `alert-container ${this.position}`;
    let i = document.createElement("div");
    (i.innerHTML = this.text), (i.className = `alert alert-${this.state}`);
    let e = "fade-in-out" === this.animation ? "fade-in-in" : "fade-up-in";
    i.classList.add(e),
      (i.style.animationDuration = this.animationDuration),
      t.appendChild(i),
      document.body.appendChild(t),
      setTimeout(() => {
        this.hide(i);
      }, 1e3 * parseFloat(this.animationDuration) + this.duration);
  }
  hide(t) {
    let i = "fade-in-out" === this.animation ? "fade-in-out" : "fade-up-out";
    t.classList.replace(t.classList[t.classList.length - 1], i),
      setTimeout(() => {
        document.body.removeChild(t.parentElement),
          (currentAlert = null),
          processQueue();
      }, 1e3 * parseFloat(this.animationDuration));
  }
}
function showAlert(t, i, e, n, s, a) {
  let r = new Alert(t, i, e, n, s, a);
  alertQueue.push(r), processQueue();
}


//    text: Input the message you want to display.
//    duration: Decide how long the alert stays on screen after its entrance animation.
//    state: Choose from “primary”, “success”, “danger”, “warning”, or “info” to set the mood of your message.
//    animation: Opt for a “fade-in-out” or “fade-up-out” visual entrance and exit.
//    animationDuration: Define how long the entrance and exit animations last.
//    position: Set the alert to appear at the “top” or “bottom”. By default, it’s set to “bottom”.