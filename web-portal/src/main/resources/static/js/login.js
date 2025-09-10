// login.js — handles sign-in for Housie
const API_BASE = localStorage.getItem('housie_api_base') || 'http://localhost:8080';
const EVENTS_PAGE = localStorage.getItem('housie_events_page') || 'events.html'; // change to your home page

const LS_USER = 'housie_current_user_name';
const LS_ROLE = 'housie_current_user_role'; // ADMIN or PLAYER
const LS_JWT  = 'jwt';

function redirectToEvents(){
  window.location.href = EVENTS_PAGE;
}

function setSession({name, role, token}, remember=false){
  localStorage.setItem(LS_USER, name);
  localStorage.setItem(LS_ROLE, role);
  localStorage.setItem(LS_JWT, token || '');
  if(!remember){
    // Optionally keep JWT in sessionStorage if you don't want persistence
    // sessionStorage.setItem(LS_JWT, token||'');
  }
}

async function loginRequest(username, password){
  // If you have a backend endpoint, uncomment and adjust:
  // const res = await fetch(`${API_BASE}/auth/login`, {
  //   method: 'POST',
  //   headers: { 'Content-Type': 'application/json' },
  //   body: JSON.stringify({ username, password })
  // });
  // if(!res.ok) throw new Error(await res.text());
  // return res.json(); // expect { token, name, role }

  // Demo-only: emulate roles by email prefix
  const isAdmin = /admin/i.test(username) || username.toLowerCase().startsWith('admin');
  return new Promise(resolve => setTimeout(() => {
    resolve({ token: 'demo.jwt.token', name: username.split('@')[0] || 'Player', role: isAdmin ? 'ADMIN' : 'PLAYER' });
  }, 600));
}

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  const errorEl = document.getElementById('error');
  const togglePwd = document.getElementById('togglePwd');
  const pwd = document.getElementById('password');

  togglePwd.addEventListener('click', () => {
    pwd.type = pwd.type === 'password' ? 'text' : 'password';
    togglePwd.textContent = pwd.type === 'password' ? '👁️' : '🙈';
  });

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    errorEl.classList.add('hidden');
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value;
    const remember = document.getElementById('remember').checked;
    if(!username || !password){
      errorEl.textContent = 'Please fill username and password.';
      errorEl.classList.remove('hidden');
      return;
    }
    try{
      const { token, name, role } = await loginRequest(username, password);
      setSession({ name, role, token }, remember);
      redirectToEvents();
    }catch(err){
      errorEl.textContent = err.message || 'Login failed';
      errorEl.classList.remove('hidden');
    }
  });

  // Demo buttons
  document.getElementById('btnDemoAdmin').addEventListener('click', async () => {
    const { token, name, role } = await loginRequest('admin@housie.app', 'admin123');
    setSession({ name, role, token }, true);
    redirectToEvents();
  });
  document.getElementById('btnDemoPlayer').addEventListener('click', async () => {
    const { token, name, role } = await loginRequest('player@housie.app', 'player123');
    setSession({ name, role, token }, true);
    redirectToEvents();
  });
});
