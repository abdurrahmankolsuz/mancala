import React from "react";
import 'primeflex/primeflex.css'
import 'primereact/resources/themes/arya-orange/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import Stone from "./Stone";
import axios from "axios";
import {GAME_MOVE_URL, GET_GAME_URL, RESPONSE_OK} from "../constants/constants";
import Result from "./Result";

class Game extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            game: this.props.game
        };

    }

    componentDidMount() {
        this.getGame();
    }


    getGame() {
        axios.get(GET_GAME_URL, {
                params: {
                    id: this.props.game.id
                }
            }
        ).then(res => {
            if (res.status === RESPONSE_OK) {
                this.setState({
                    game: res.data
                })
            }
        });
    }

    sortPieces(gameId, pocketId) {
        axios.post(GAME_MOVE_URL, {
                gameId: gameId,
                pocketId: pocketId
            }
        ).then(res => {
            if (res.status === RESPONSE_OK) {
                this.setState({
                    game: res.data
                })
            }
        }).catch(err => {
            alert(err.response.data.message)
        });
    }

    render() {
        if (this.state.game.gameStatus === "OVER") {
            const northStones = this.state.game.board.pockets[13].quantityOfStones;
            const southStones = this.state.game.board.pockets[6].quantityOfStones;
            const winner = northStones > southStones ? "Winner is PLAYER ONE" : "Winner is PLAYER TWO";
            return (
                <Result winner={winner} northStones={northStones} southStones={southStones}/>
            )
        } else {
            return (
                <div className="p-megamenu">
                    <h2><span
                        className={this.state.game.playerTurn === "PLAYER_ONE" ? 'badge badge-success' : 'badge badge-secondary'}
                        style={{display: 'block'}}>Player 1</span>
                    </h2>
                    <div className="grid">
                        <div className="grid-item grid-item-main-pocket grid-item--width2 grid-item--height2">
                            <Stone stone={this.state.game.board.pockets[13].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 13)}
                             className="grid-item grid-item-pocket">
                            <Stone stone={this.state.game.board.pockets[12].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 12)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[11].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 11)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[10].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 10)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[9].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 9)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[8].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 8)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[7].quantityOfStones}/>
                        </div>


                        <div onClick={() => this.sortPieces(this.state.game.id, 1)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[0].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 2)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[1].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 3)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[2].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 4)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[3].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 5)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[4].quantityOfStones}/>
                        </div>
                        <div onClick={() => this.sortPieces(this.state.game.id, 6)}
                             className="grid-item grid-item-pocket">
                            <Stone
                                stone={this.state.game.board.pockets[5].quantityOfStones}/>
                        </div>
                        <div
                            className="grid-item grid-item-main-pocket grid-item--width2 grid-item--height2 last-main-pocket">
                            <Stone stone={this.state.game.board.pockets[6].quantityOfStones}/>
                        </div>
                    </div>
                    <h2><span name="player2"
                              className={this.state.game.playerTurn === "PLAYER_TWO" ? 'badge badge-success' : 'badge badge-secondary'}
                              style={{display: 'block'}}>Player 2</span></h2>
                </div>
            );
        }
    }
}

export default Game