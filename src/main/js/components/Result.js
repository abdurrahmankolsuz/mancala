import {Card} from "primereact/card";
import React from "react";
import {Button} from "primereact/button";
import * as ReactDOM from "react-dom";
import App from "../App";

class Result extends React.Component {

    constructor(props) {
        super(props);
        this.backToStart = this.backToStart.bind(this);
    }


    backToStart() {
        ReactDOM.render(
            <App/>,
            document.getElementById('root')
        )
    }

    render() {
        const footer = <span><Button label="Back To Start" icon="pi pi-angle-left" onClick={this.backToStart}
                                     style={{marginRight: '.25em'}}/></span>;
        return (
            <Card footer={footer}>
                <div className="flex flex-column">
                    <div
                        className="flex align-items-center justify-content-center"> GAME OVER
                    </div>
                    <div
                        className="flex align-items-center justify-content-center"> PLAYER ONE STONES
                        : {this.props.northStones}
                    </div>
                    <div
                        className="flex align-items-center justify-content-center">PLAYER TWO STONES : {this.props.southStones}
                    </div>
                    <div
                        className="flex align-items-center justify-content-center">  {this.props.winner}
                    </div>
                </div>
            </Card>);
    }

}
export default Result