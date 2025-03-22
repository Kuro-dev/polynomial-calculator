package org.kurodev.ui.components.userinput;

import org.kurodev.polynomials.Polynomial;
import org.kurodev.ui.util.AsyncPolynomialOperations;
import org.kurodev.ui.components.JLabelledTextField;
import org.kurodev.ui.input.NumberInput;
import org.kurodev.ui.input.PolynomialInput;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class InputPanel extends JPanel {
    private final NumberInput exponentInput = new NumberInput(0);
    private final NumberInput baseInput = new NumberInput(2);
    private final PolynomialInput polynomialInput = new PolynomialInput();
    private final JButton calculateButton = new JButton("Calculate");
    private final JProgressBar progressBar = new JProgressBar();
    private final Consumer<CalculationRequest> buttonConsumer;
    private CompletableFuture<List<Polynomial>> irreducibles = CompletableFuture.completedFuture(List.of());

    public InputPanel(Consumer<CalculationRequest> buttonConsumer) {
        this.buttonConsumer = buttonConsumer;
        init();
    }

    private void init() {
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(layout);
        // COMPONENT INITIALISATION
        progressBar.setVisible(false);
        progressBar.setIndeterminate(true);
        progressBar.setMaximumSize(new Dimension(80, progressBar.getPreferredSize().height));

        exponentInput.addFocusListener(this::tryFetchPolys);
        baseInput.addFocusListener(this::tryFetchPolys);
        this.add(new JLabelledTextField("Basis:", baseInput, 50));
        this.add(new JLabelledTextField("Exponent:", exponentInput, 50));
        this.add(new JLabelledTextField("Irreducible Polynomial:", polynomialInput, 200));
        this.add(calculateButton);
        this.add(progressBar);
        calculateButton.setEnabled(false);
        // STATE INITIALISATION
        polynomialInput.setEnabled(false);
        exponentInput.addChangeListener(text -> {
            polynomialInput.setText("");
            if (text.isBlank()) {
                polynomialInput.setEnabled(false);
                return;
            }
            int num = Integer.parseInt(text);
            polynomialInput.setEnabled(num > 0);
        });
        baseInput.addChangeListener(_ -> polynomialInput.setText(""));

        calculateButton.addActionListener(e -> triggerEvent());
        polynomialInput.addChangeListener(text -> {
            if (text.isBlank()) {
                calculateButton.setEnabled(false);
                return;
            }
            Polynomial.of(text);
            calculateButton.setEnabled(true);
        });
    }

    private void triggerEvent() {
        int base = Integer.parseInt(baseInput.getText());
        int exponent = Integer.parseInt(exponentInput.getText());
        Polynomial irreducible = Polynomial.of(polynomialInput.getText());
        buttonConsumer.accept(new CalculationRequest(base, exponent, irreducible));
        calculateButton.setEnabled(false);
    }

    private void tryFetchPolys(boolean focussed) {
        if (focussed) return;

        boolean canFetchPolys = !exponentInput.getText().isBlank() && !baseInput.getText().isBlank();
        if (canFetchPolys) {
            int base = Integer.parseInt(baseInput.getText());
            int exp = Integer.parseInt(exponentInput.getText());
            progressBar.setVisible(true);
            irreducibles = AsyncPolynomialOperations.findIrreducibles(base, exp);
            irreducibles.whenComplete((polynomials, throwable) -> {
                progressBar.setVisible(false);
                if (throwable == null && polynomials != null) {
                    polynomialInput.setSuggestions(polynomials);
                }
            });
        }
    }
}
