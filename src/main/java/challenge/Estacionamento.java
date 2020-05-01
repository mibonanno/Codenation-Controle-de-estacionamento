package challenge;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private static final int LIMITE_VAGAS = 10;
    private static final int IDADE_MAXIMA_SEM_PREFERENCIA = 55;
    private final List<Carro> carrosEstacionados = new ArrayList<>();

    public void estacionar(Carro carro) {
        validarMotoristaEHabilitacao(carro);
        verificarLotacaoLiberarVagaSePossivel();
        carrosEstacionados.add(carro);
    }

    public int carrosEstacionados() {
        return carrosEstacionados.size();
    }

    public boolean carroEstacionado(Carro carro) {
        return carrosEstacionados.contains(carro);
    }

    private void validarMotoristaEHabilitacao(Carro carro) {
        if (carro.getMotorista() == null) {
            throw new EstacionamentoException("Carro autônomo não é aceito");
        }
        if (carro.getMotorista().getIdade() < 18) {
            throw new EstacionamentoException("Motorista não tem idade suficiente para dirigir");
        }
        if (carro.getMotorista().getHabilitacao() == null) {
            throw new NullPointerException();
        }
        if (carro.getMotorista().getPontos() > 20) {
            throw new EstacionamentoException("Habilitação está suspensa");
        }
    }

    private void verificarLotacaoLiberarVagaSePossivel() {
        if (carrosEstacionados.size() < LIMITE_VAGAS) {
            return;
        }
        Carro proximoCarroASair = obterProximoSair();
        carrosEstacionados.remove(proximoCarroASair);
    }

    private Carro obterProximoSair() {
        for (Carro carro : carrosEstacionados) {
            if (carro.getMotorista().getIdade() <= IDADE_MAXIMA_SEM_PREFERENCIA) {
                return carro;
            }
        }
        throw new EstacionamentoException("Não há vaga. Todos os motoristas estacionados tem idade acima de " + IDADE_MAXIMA_SEM_PREFERENCIA);
    }
}