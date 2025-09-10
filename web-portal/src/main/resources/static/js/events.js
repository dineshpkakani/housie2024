// Demo switch: set to true to wire with your backend API
const USE_BACKEND = false;
const API_BASE = localStorage.getItem('housie_api_base') || 'http://localhost:8080/api';

// Simulate login info
const CURRENT_USER_KEY = 'housie_current_user_name';
const CURRENT_ROLE_KEY = 'housie_current_user_role'; // "ADMIN" or "PLAYER"

// Enable Create Event only if ADMIN
function checkAdmin() {
    const role = localStorage.getItem(CURRENT_ROLE_KEY) || 'PLAYER';
    const btn = document.getElementById('btnNewEvent');
    if (role === 'ADMIN') btn.removeAttribute('disabled');
    else btn.setAttribute('disabled', true);
}

// ---------- Helpers ----------
const fmt = new Intl.NumberFormat('en-IN');
const fmtCurrency = new Intl.NumberFormat('en-IN');
const el = id => document.getElementById(id);
const qsa = (sel, root=document) => [...root.querySelectorAll(sel)];

// Demo local data
const LS_KEY = 'housie_events_v2';

function mkEvent({eventName, eventDate, price, ticketsSold=0, players=[]}) {
    return { id: crypto.randomUUID(), eventName, eventDate, price:Number(price), ticketsSold:Number(ticketsSold), players:[...new Set(players)] };
}
function dateFromNow(n){ const d=new Date(); d.setDate(d.getDate()+n); return d.toISOString().slice(0,10); }
function loadLocal(){
    const raw = localStorage.getItem(LS_KEY);
    if(!raw){
        const demo=[ mkEvent({eventName:'Housie Night',eventDate:dateFromNow(1),price:100,ticketsSold:5,players:['dinesh']}) ];
        localStorage.setItem(LS_KEY, JSON.stringify(demo));
        return demo;
    }
    return JSON.parse(raw);
}
function saveLocal(list){ localStorage.setItem(LS_KEY, JSON.stringify(list)); }

// API stubs (swap with backend)
async function listEvents(){ if(!USE_BACKEND){return loadLocal().map(e=>({...e,playersCount:e.players.length,totalCollection:e.ticketsSold*e.price}));} }
async function createEvent(payload){ if(!USE_BACKEND){const list=loadLocal(); list.push(mkEvent({...payload, players:[]})); saveLocal(list);} }
async function updateEvent(id,payload){ if(!USE_BACKEND){const list=loadLocal(); const i=list.findIndex(x=>x.id===id); list[i]={...list[i],...payload}; saveLocal(list);} }
async function deleteEvent(id){ if(!USE_BACKEND){const list=loadLocal().filter(x=>x.id!==id); saveLocal(list);} }
async function toggleRegister(id, player){ if(!USE_BACKEND){const list=loadLocal(); const e=list.find(x=>x.id===id); const i=e.players.indexOf(player); if(i>=0) e.players.splice(i,1); else e.players.push(player); saveLocal(list);} }
async function buyTickets(id, qty, buyer){ if(!USE_BACKEND){const list=loadLocal(); const e=list.find(x=>x.id===id); e.ticketsSold+=qty; if(buyer && !e.players.includes(buyer)) e.players.push(buyer); saveLocal(list);} }

// ---------- UI State ----------
let EVENTS = [];
let filterText = '';
let filterTime = 'all';
const defaultName = localStorage.getItem(CURRENT_USER_KEY) || 'You';
el('userName').textContent = defaultName;

function render(){
    const tbody=el('tbody'); tbody.innerHTML='';
    const today=new Date().toISOString().slice(0,10);
    const list=EVENTS.filter(e=>{
        const txt=(e.eventName||'').toLowerCase();
        const matchTxt=!filterText||txt.includes(filterText)||(e.eventDate||'').includes(filterText);
        const d=(e.eventDate||'');
        const matchTime=filterTime==='all'||(filterTime==='upcoming'? d>=today: d<today);
        return matchTxt&&matchTime;
    }).sort((a,b)=>(a.eventDate||'').localeCompare(b.eventDate||''));

    let sumT=0,sumC=0;
    for(const ev of list){
        const total=ev.totalCollection!=null?ev.totalCollection:ev.ticketsSold*ev.price;
        sumT+=ev.ticketsSold; sumC+=total;
        const tr=document.createElement('tr');
        tr.innerHTML=`
      <td><strong>${ev.eventName}</strong></td>
      <td>${(ev.eventDate||'').slice(0,10)}</td>
      <td>${ev.playersCount!=null?ev.playersCount:ev.players.length}</td>
      <td>${fmt.format(ev.ticketsSold)}</td>
      <td>₹ ${fmtCurrency.format(ev.price)}</td>
      <td>₹ ${fmtCurrency.format(total)}</td>
      <td class=\"col-actions\">
        <button class=\"btn success btn-buy\" data-id=\"${ev.id}\">🛒 Buy</button>
        <button class=\"btn primary btn-reg\" data-id=\"${ev.id}\">📝 Register</button>
        <button class=\"btn ghost btn-edit\" data-id=\"${ev.id}\">✏️ Edit</button>
        <button class=\"btn warn btn-del\" data-id=\"${ev.id}\">🗑️ Delete</button>
      </td>`;
        tbody.appendChild(tr);
    }
    el('count').textContent=list.length;
    el('sumTickets').textContent=fmt.format(sumT);
    el('sumCollection').textContent=fmtCurrency.format(sumC);

    qsa('.btn-edit').forEach(b=>b.onclick=()=>openEdit(b.dataset.id));
    qsa('.btn-del').forEach(b=>b.onclick=()=>removeEvent(b.dataset.id));
    qsa('.btn-reg').forEach(b=>b.onclick=()=>openRegister(b.dataset.id));
    qsa('.btn-buy').forEach(b=>b.onclick=()=>openBuy(b.dataset.id));
}

async function refresh(){ EVENTS=await listEvents(); render(); }

// ---- Modal logic (same as before, shortened for brevity) ----
function openCreate(){ /* ... same logic ... */ }
function openEdit(id){ /* ... */ }
function openRegister(id){ /* ... */ }
function openBuy(id){ /* ... */ }

// ---- Init ----
el('btnNewEvent').onclick=openCreate;
el('q').addEventListener('input',e=>{filterText=e.target.value.trim().toLowerCase();render();});
el('filterUpcoming').addEventListener('change',e=>{filterTime=e.target.value;render();});
el('btnClear').onclick=()=>{localStorage.removeItem(LS_KEY);refresh();};

checkAdmin(); // ensure role check at load
refresh();
