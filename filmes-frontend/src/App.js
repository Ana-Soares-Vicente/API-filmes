import { useEffect, useState } from "react";

const T = {
  purple: "#7F77DD",
  purpleLight: "#EEEDFE",
  purpleDark: "#3C3489",
  teal: "#1D9E75",
  tealLight: "#E1F5EE",
  amber: "#BA7517",
  amberLight: "#FAEEDA",
  gray50: "#F1EFE8",
  gray200: "#B4B2A9",
  gray600: "#5F5E5A",
  gray900: "#2C2C2A",
  white: "#ffffff",
  red: "#E24B4A",
};

const card = {
  background: T.white,
  border: "1px solid #E5E3DE",
  borderRadius: 14,
  padding: "1.5rem",
  marginBottom: "0",
};

const inputStyle = {
  width: "100%",
  padding: "10px 14px",
  border: "1px solid " + T.gray200,
  borderRadius: 8,
  fontSize: 15,
  outline: "none",
  boxSizing: "border-box",
  background: T.white,
  color: T.gray900,
};

const btnPrimary = {
  padding: "10px 20px",
  background: T.purple,
  color: T.white,
  border: "none",
  borderRadius: 8,
  fontSize: 14,
  fontWeight: 500,
  cursor: "pointer",
  whiteSpace: "nowrap",
};

const label = {
  fontSize: 13,
  fontWeight: 500,
  color: T.gray600,
  marginBottom: 6,
  display: "block",
};

const sectionTitle = {
  fontSize: 16,
  fontWeight: 500,
  color: T.gray900,
  margin: "0 0 1rem 0",
  display: "flex",
  alignItems: "center",
  gap: 8,
};

function badge(color, bg) {
  return {
    display: "inline-flex",
    alignItems: "center",
    padding: "2px 10px",
    borderRadius: 20,
    fontSize: 12,
    fontWeight: 500,
    color: color,
    background: bg,
  };
}

function Stars({ value }) {
  if (value == null) return <span style={{ color: T.gray200, fontSize: 13 }}>sem avaliações</span>;
  const full = Math.round(value);
  return (
    <span style={{ display: "inline-flex", gap: 2, alignItems: "center" }}>
      {[1, 2, 3, 4, 5].map(i => (
        <span key={i} style={{ fontSize: 14, color: i <= full ? T.amber : T.gray200 }}>★</span>
      ))}
      <span style={{ fontSize: 13, color: T.gray600, marginLeft: 4 }}>{value.toFixed(1)}</span>
    </span>
  );
}

function Icon({ children, bg, color }) {
  return (
    <span style={{
      width: 28, height: 28, borderRadius: 8,
      background: bg, color: color,
      display: "inline-flex", alignItems: "center", justifyContent: "center",
      fontSize: 14, fontWeight: 500, flexShrink: 0,
    }}>
      {children}
    </span>
  );
}

function Empty({ text }) {
  return (
    <div style={{ padding: "1rem 0", color: T.gray200, fontSize: 14, textAlign: "center" }}>
      {text}
    </div>
  );
}

function App() {
  const [usuarios, setUsuarios] = useState([]);
  const [filmes, setFilmes] = useState([]);
  const [nomeUsuario, setNomeUsuario] = useState("");
  const [tituloFilme, setTituloFilme] = useState("");
  const [usuarioId, setUsuarioId] = useState("");
  const [filmeId, setFilmeId] = useState("");
  const [nota, setNota] = useState("");
  const [medias, setMedias] = useState({});
  const [toast, setToast] = useState(null);

  useEffect(() => {
    carregarUsuarios();
    carregarFilmes();
  }, []);

  function showToast(msg, ok) {
    if (ok === undefined) ok = true;
    setToast({ msg, ok });
    setTimeout(() => setToast(null), 2800);
  }

  function carregarUsuarios() {
    fetch("http://localhost:8080/usuarios")
      .then(res => res.json())
      .then(data => setUsuarios(data))
      .catch(() => {});
  }

  function carregarFilmes() {
    fetch("http://localhost:8080/filmes")
      .then(res => res.json())
      .then(data => {
        setFilmes(data);
        carregarMedias(data);
      })
      .catch(() => {});
  }

  function cadastrarUsuario() {
    if (!nomeUsuario.trim()) return;
    fetch("http://localhost:8080/usuarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome: nomeUsuario }),
    }).then(() => {
      setNomeUsuario("");
      carregarUsuarios();
      showToast("Usuário cadastrado!");
    });
  }

  function cadastrarFilme() {
    if (!tituloFilme.trim()) return;
    fetch("http://localhost:8080/filmes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ titulo: tituloFilme }),
    }).then(() => {
      setTituloFilme("");
      carregarFilmes();
      showToast("Filme cadastrado!");
    });
  }

  function enviarAvaliacao() {
    if (!usuarioId || !filmeId || !nota) {
      showToast("Preencha todos os campos.", false);
      return;
    }
    fetch("http://localhost:8080/avaliacoes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        usuarioId: Number(usuarioId),
        filmeId: Number(filmeId),
        nota: Number(nota),
      }),
    }).then(() => {
      setNota("");
      carregarFilmes();
      showToast("Avaliação enviada!");
    });
  }

  function carregarMedias(listaFilmes) {
    listaFilmes.forEach(filme => {
      fetch("http://localhost:8080/avaliacoes/media/" + filme.id)
        .then(res => res.json())
        .then(media => {
          setMedias(prev => ({ ...prev, [filme.id]: media }));
        })
        .catch(() => {});
    });
  }

  return (
    <div style={{ background: T.gray50, minHeight: "100vh", fontFamily: "system-ui, sans-serif" }}>

      {toast && (
        <div style={{
          position: "fixed", bottom: 24, right: 24, zIndex: 999,
          background: toast.ok ? T.teal : T.red,
          color: T.white, padding: "12px 20px",
          borderRadius: 10, fontSize: 14, fontWeight: 500,
          boxShadow: "0 4px 16px rgba(0,0,0,.12)",
        }}>
          {toast.msg}
        </div>
      )}

      <div style={{
        background: T.white,
        borderBottom: "1px solid #E5E3DE",
        padding: "0 24px",
        display: "flex", alignItems: "center", gap: 12,
        height: 60,
      }}>
        <span style={{ fontSize: 22 }}>🎬</span>
        <h1 style={{ margin: 0, fontSize: 18, fontWeight: 500, color: T.gray900 }}>
          Sistema de Filmes
        </h1>
        <div style={{ marginLeft: "auto", display: "flex", gap: 8 }}>
          <span style={badge(T.purpleDark, T.purpleLight)}>{usuarios.length} usuários</span>
          <span style={badge(T.teal, T.tealLight)}>{filmes.length} filmes</span>
        </div>
      </div>

      <div style={{
        maxWidth: 960, margin: "0 auto", padding: "2rem 24px",
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
        gap: "1.5rem",
        alignItems: "start",
      }}>

        <div style={card}>
          <p style={sectionTitle}>
            <Icon bg={T.purpleLight} color={T.purple}>U</Icon>
            Cadastrar usuário
          </p>
          <label style={label}>Nome</label>
          <div style={{ display: "flex", gap: 8 }}>
            <input
              style={inputStyle}
              value={nomeUsuario}
              onChange={e => setNomeUsuario(e.target.value)}
              onKeyDown={e => e.key === "Enter" && cadastrarUsuario()}
              placeholder="Ex: Ana Silva"
            />
            <button style={btnPrimary} onClick={cadastrarUsuario}>+</button>
          </div>
          <div style={{ marginTop: "1rem" }}>
            {usuarios.length === 0
              ? <Empty text="Nenhum usuário ainda" />
              : usuarios.map(u => (
                <div key={u.id} style={{
                  display: "flex", alignItems: "center", gap: 10,
                  padding: "8px 0",
                  borderBottom: "1px solid #F1EFE8",
                }}>
                  <div style={{
                    width: 30, height: 30, borderRadius: "50%",
                    background: T.purpleLight, color: T.purple,
                    display: "flex", alignItems: "center", justifyContent: "center",
                    fontSize: 12, fontWeight: 500, flexShrink: 0,
                  }}>
                    {u.nome.charAt(0).toUpperCase()}
                  </div>
                  <span style={{ fontSize: 14, color: T.gray900 }}>{u.nome}</span>
                </div>
              ))
            }
          </div>
        </div>

        <div style={card}>
          <p style={sectionTitle}>
            <Icon bg={T.tealLight} color={T.teal}>F</Icon>
            Cadastrar filme
          </p>
          <label style={label}>Título</label>
          <div style={{ display: "flex", gap: 8 }}>
            <input
              style={inputStyle}
              value={tituloFilme}
              onChange={e => setTituloFilme(e.target.value)}
              onKeyDown={e => e.key === "Enter" && cadastrarFilme()}
              placeholder="Ex: Inception"
            />
            <button style={{ ...btnPrimary, background: T.teal }} onClick={cadastrarFilme}>+</button>
          </div>
          <div style={{ marginTop: "1rem" }}>
            {filmes.length === 0
              ? <Empty text="Nenhum filme ainda" />
              : filmes.map(f => (
                <div key={f.id} style={{
                  display: "flex", alignItems: "center", justifyContent: "space-between",
                  padding: "8px 0",
                  borderBottom: "1px solid #F1EFE8",
                  gap: 8,
                }}>
                  <span style={{ fontSize: 14, color: T.gray900 }}>{f.titulo}</span>
                  <Stars value={medias[f.id]} />
                </div>
              ))
            }
          </div>
        </div>

        <div style={{ ...card, gridColumn: "1 / -1" }}>
          <p style={sectionTitle}>
            <Icon bg={T.amberLight} color={T.amber}>★</Icon>
            Avaliar filme
          </p>
          <div style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))",
            gap: "1rem",
            alignItems: "end",
          }}>
            <div>
              <label style={label}>Usuário</label>
              <select style={inputStyle} value={usuarioId} onChange={e => setUsuarioId(e.target.value)}>
                <option value="">Selecione usuário</option>
                {usuarios.map(u => (
                  <option key={u.id} value={u.id}>{u.nome}</option>
                ))}
              </select>
            </div>

            <div>
              <label style={label}>Filme</label>
              <select style={inputStyle} value={filmeId} onChange={e => setFilmeId(e.target.value)}>
                <option value="">Selecione filme</option>
                {filmes.map(f => (
                  <option key={f.id} value={f.id}>{f.titulo}</option>
                ))}
              </select>
            </div>

            <div>
              <label style={label}>Nota (1 a 5)</label>
              <div style={{ display: "flex", gap: 6 }}>
                {[1, 2, 3, 4, 5].map(n => (
                  <button
                    key={n}
                    onClick={() => setNota(String(n))}
                    style={{
                      flex: 1,
                      padding: "10px 0",
                      borderRadius: 8,
                      border: "1px solid " + (nota === String(n) ? T.amber : T.gray200),
                      background: nota === String(n) ? T.amberLight : T.white,
                      color: nota === String(n) ? T.amber : T.gray600,
                      cursor: "pointer",
                      fontSize: 15,
                      fontWeight: 500,
                    }}
                  >
                    {n}
                  </button>
                ))}
              </div>
            </div>

            <div>
              <button
                style={{ ...btnPrimary, width: "100%", background: T.amber }}
                onClick={enviarAvaliacao}
              >
                Enviar avaliação
              </button>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default App;