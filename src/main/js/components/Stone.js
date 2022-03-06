import React from 'react'

export default class Stone extends React.Component {
    createStones() {
        let stones = [];
        for (let j = 0; j < this.props.stone; j++) {
            stones.push(<div key={Math.random()} className="grid-item-stone"><img className="stoneImage"
                                                                                  src="images/stone.png"/></div>)
        }
        return stones;
    }


    render() {
        return (
            <div className="grid-stone">
                {this.createStones()}
            </div>
        )
    }

}