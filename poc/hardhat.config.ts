import { HardhatUserConfig } from "hardhat/config";
import "@nomicfoundation/hardhat-toolbox";

const config: HardhatUserConfig = {
  solidity: "0.8.17",
  networks: {
    hardhat: {
      chainId: 1337 // We set 1337 to make interacting with MetaMask simpler
    }
  },
};

export default config;
