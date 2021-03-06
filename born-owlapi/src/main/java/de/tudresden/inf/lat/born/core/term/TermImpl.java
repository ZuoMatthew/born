package de.tudresden.inf.lat.born.core.term;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of a term.
 * 
 * @author Julian Mendez
 *
 */
public class TermImpl implements Term {

	private final String name;
	private final List<Term> arguments = new ArrayList<>();
	private final Term.Type termType;

	/**
	 * Constructs an empty term.
	 */
	public TermImpl() {
		this.name = "";
		this.termType = Term.Type.ATOM;
	}

	/**
	 * Constructs a new variable.
	 * 
	 * @param name
	 *            variable name
	 */
	public TermImpl(String name) {
		Objects.requireNonNull(name);
		if (isLong(name)) {
			this.termType = Term.Type.LONG;
		} else if (isDouble(name)) {
			this.termType = Term.Type.DOUBLE;
		} else if (isVariable(name)) {
			this.termType = Term.Type.VARIABLE;
		} else {
			this.termType = Term.Type.ATOM;
		}
		this.name = name;
	}

	/**
	 * Constructs a new formula. If the arguments is an empty list, this is
	 * considered a constant.
	 * 
	 * @param name
	 *            formula name
	 * @param arguments
	 *            arguments
	 */
	public TermImpl(String name, List<Term> arguments) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(arguments);
		if (isLong(name) || isDouble(name) || isVariable(name)) {
			throw new IllegalArgumentException("Invalid functor: '" + name + "'. ");
		}
		if (arguments.isEmpty()) {
			this.termType = Term.Type.ATOM;
		} else {
			this.termType = Term.Type.COMPOUND_TERM;
		}
		this.name = name;
		this.arguments.addAll(arguments);
	}

	/**
	 * Constructs a new term. If the arguments is an empty list, this is
	 * considered a constant.
	 * 
	 * @param leftTerm
	 *            left term
	 * @param infixOperator
	 *            infix operator
	 * @param rightTerm
	 *            right term
	 */
	public TermImpl(Term leftTerm, String infixOperator, Term rightTerm) {
		Objects.requireNonNull(leftTerm);
		Objects.requireNonNull(infixOperator);
		Objects.requireNonNull(rightTerm);
		this.name = infixOperator;
		this.arguments.add(leftTerm);
		this.arguments.add(rightTerm);
		this.termType = Term.Type.INFIX_OPERATOR;
	}

	public boolean isVariable(String name) {
		Objects.requireNonNull(name);
		if (Objects.isNull(name) || name.trim().isEmpty()) {
			return false;
		} else {
			char firstChar = name.trim().charAt(0);
			return ('A' <= firstChar && firstChar <= 'Z') || (firstChar == '_');
		}
	}

	public boolean isLong(String name) {
		Objects.requireNonNull(name);
		if (Objects.isNull(name) || name.trim().isEmpty()) {
			return false;
		} else {
			try {
				Long.parseLong(name.trim());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	public boolean isDouble(String name) {
		Objects.requireNonNull(name);
		if (Objects.isNull(name) || name.trim().isEmpty()) {
			return false;
		} else {
			try {
				Double.parseDouble(name.trim());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Term> getArguments() {
		return this.arguments;
	}

	@Override
	public Term.Type getType() {
		return this.termType;
	}

	@Override
	public String asString() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(this.name);
		if (this.termType.equals(Term.Type.COMPOUND_TERM)) {
			sbuf.append(Symbol.PAR_A_CHAR);
			Iterator<Term> it = this.arguments.iterator();
			while (it.hasNext()) {
				Term current = it.next();
				sbuf.append(current);
				if (it.hasNext()) {
					sbuf.append(Symbol.COMMA_CHAR);
					sbuf.append(Symbol.SPACE_CHAR);
				}
			}
			sbuf.append(Symbol.PAR_B_CHAR);
		}
		return sbuf.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Term)) {
			return false;
		} else {
			Term other = (Term) obj;
			return getType().equals(other.getType()) && getName().equals(other.getName())
					&& getArguments().equals(other.getArguments());
		}
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + 0x1F * (this.arguments.hashCode() + 0x1F * Term.Type.values().hashCode());
	}

	@Override
	public String toString() {
		return asString();
	}

}
