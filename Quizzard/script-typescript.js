let data_db = '';
const db_raw = [];
const db_c = [];
var sel = -1;
var elems = 0;
const memory = [];
var mem_len = 0;

<!-- Fetch content from another webpage -->
let loc = 'https://raw.githubusercontent.com/220509-web-dev/FoundationProject-Josiah/main/Quizzard/db-ts.html';
fetch(loc)
  .then(response => response.text())
  .then(data => set_db(data));

function set_db(text) {
 <!-- sets global variable data_db -->
 <!-- Runs after fetch completes -->
 window.data_db = text;
 window.db_raw = window.data_db.split(";;;;;;;;");

 <!-- Set db_c to a blank 2-d array of proper length -->
 window.elems = window.db_raw.length;
 window.db_c = [...Array(window.elems)].map(x => Array(2).fill(''));

 for (var i = 0; i < window.elems; i++) {
   window.db_c[i] = window.db_raw[i].split(";;;;");
 }
}

function getRandomInt() {
  <!-- window.elems is >= 2 -->
  let max = window.elems-1-memory.length;
  let rnum = Math.floor(Math.random() * (max+1));
  <!-- count how many in memory are <= rnum -->
  var i = 0;
  var final_rnum = rnum-0;
  <!-- step -->
  while (i <= final_rnum) {
    if (memory.includes(i)) { final_rnum++; }
    i++;
  }
  var adjust = final_rnum - rnum;
  memory.push(final_rnum);
  var max_size = Math.floor(window.elems /2);
  if (memory.length > max_size) { memory.shift(); }
  console.log("adjust="+ adjust+ " rnum="+ rnum+ " memory={"+memory.toString()+"}");
  return final_rnum;
}
function set_sel(val) {
  window.sel = val;
}

function remember(val) {
  var max_size = Math.floor(window.elems /2);
  if (max_size > 0) {
    memory.push(val);
    if (memory.length > max_size) { memory.shift(val); }
  }
}
function rnd_sel() {
  if (window.elems > 1) {
    var rnd_int = getRandomInt();
    set_sel(rnd_int);
  } else if (window.elems == 1) {set_sel(0);}
}

function repl_q() {
  rnd_sel();
  document.getElementById('question').innerHTML = window.db_c[window.sel][0];
  document.getElementById('answer').innerHTML = "<p>Click to see answer</p>";
  <!-- window.db_c[window.sel][1] -->
}
function repl_a() {
  document.getElementById('answer').innerHTML = window.db_c[window.sel][1];
}