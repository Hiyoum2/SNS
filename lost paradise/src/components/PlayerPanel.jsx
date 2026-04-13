function PlayerPanel({ player }) {
  return (
    <div>
      <p>{player.name}</p>
      <p>HP: {player.hp}</p>
      <p>SP: {player.sp}</p>
    </div>
  )
}

export default PlayerPanel