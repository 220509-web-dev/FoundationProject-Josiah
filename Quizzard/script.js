let data_db = '';
const db_raw = [];
const db_c = [];
var sel = 0;
var elems = 0;
<!-- Fetch content from another webpage -->
let loc = 'https://raw.githubusercontent.com/220509-web-dev/FoundationProject-Josiah/main/Quizzard/db.txt';
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
function getRandomInt(max) {
  <!-- Returns integer in range [0,max] meaning 0 or max could be returned -->
  return Math.floor(Math.random() * (max+1));
}
function set_sel(val) {
  window.sel = val;
}
function rnd_sel() {
  set_sel(getRandomInt(window.elems-1));
}

function repl_with(text) {
  rnd_sel();
  document.getElementById('question').innerHTML = window.db_c[window.sel][0];
  document.getElementById('answer').innerHTML = window.db_c[window.sel][1];
}