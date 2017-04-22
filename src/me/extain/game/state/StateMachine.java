package me.extain.game.state;

import java.util.Stack;

public class StateMachine {
	
	private Stack<State> states = new Stack<State>();
	
	public State peek() {
		return states.peek();
	}
	
	public void pop() {
		this.states.pop();
	}
	
	public void setState(State state) {
		this.states.push(state);
		this.peek().init();
	}
	
	public void changeState(State state) {
		this.pop();
		this.setState(state);
	}
}
