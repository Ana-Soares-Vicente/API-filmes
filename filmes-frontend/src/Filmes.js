import { useEffect, useState } from "react";

function App() {
  const [filmes, setFilmes] = useState([]);
  const [titulo, setTitulo] = useState("");

  useEffect(() => {
    carregarFilmes();
  }, []);

  function carregarFilmes() {
    fetch("http://localhost:8080/filmes")
      .then(res => res.json())
      .then(data => setFilmes(data));
  }

  function cadastrarFilme() {
    fetch("http://localhost:8080/filmes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ titulo })
    }).then(() => {
      setTitulo("");
      carregarFilmes(); // atualiza lista
    });
  }

  return (
    <div>
      <h1>Filmes</h1>

      <input
        value={titulo}
        onChange={e => setTitulo(e.target.value)}
        placeholder="Nome do filme"
      />

      <button onClick={cadastrarFilme}>
        Cadastrar
      </button>

      {filmes.map(f => (
        <p key={f.id}>{f.titulo}</p>
      ))}
    </div>
  );
}
export default App;