function CardItem({ card }) {
  return (
    <div>
      <p>{card.name}</p>
      <p>Cost: {card.cost}</p>
    </div>
  )
}

export default CardItem