import Blog from "../../components/Home/Blog";
import Contact from "../../components/Home/Contact";
import HomeMain from "../../components/Home/HomeMain";
import Milestones from "../../components/Home/Milestones";
import Portfolio from "../../components/Home/Portfolio";
import Pricing from "../../components/Home/Pricing";
import ScrollToTop from "../../components/ScrollToTop";
import Services from "../../components/Home/Services";
import Skills from "../../components/Home/Skills";
import Testimonials from "../../components/Home/Testimonials";
import Video from "../../components/Home/Video";
import React from "react";
import { motion } from "framer-motion";

export default function Home() {
  return (
    <motion.div initial="hidden" animate="show">
      <ScrollToTop />
      <HomeMain />
      <Services />
      <Portfolio />
      <Milestones />
      <Blog />
      <Video />
      <Pricing />
      <Testimonials />
      {/* <Skills />
      <Contact /> */}
    </motion.div>
  );
}
