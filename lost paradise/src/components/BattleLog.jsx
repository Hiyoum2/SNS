function BattleLog({ logs }) {
  return (
    <div>
      {logs.map((log, i) => (
        <p key={i}>{log}</p>
      ))}
    </div>
  )
}

export default BattleLog